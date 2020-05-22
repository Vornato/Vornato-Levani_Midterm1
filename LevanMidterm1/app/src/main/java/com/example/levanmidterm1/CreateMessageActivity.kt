package com.example.levanmidterm1

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_create_message.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class CreateMessageActivity : AppCompatActivity() {

    private val imageName = UUID.randomUUID().toString() + ".jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_message)

        chooseImageButton.setOnClickListener {
            chooseImageClicked(it)
        }

        nextButton.setOnClickListener {
            nextClicked(it)
        }
    }

    private fun getPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }


    private fun chooseImageClicked(view: View) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                createMessageImageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    private fun nextClicked(view: View) {
        createMessageImageView.isDrawingCacheEnabled = true
        createMessageImageView.buildDrawingCache()
        val bitmap = (createMessageImageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = FirebaseStorage.getInstance().reference.child("images").child(imageName).putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(this, "Upload has failed!", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            var downloadUrl: String

             FirebaseStorage.getInstance()
                .reference.child("images/$imageName").downloadUrl.addOnCompleteListener {
                    downloadUrl = it.result.toString()
                     Log.i("LEO_URL", downloadUrl)

                     val intent = Intent(this, ChooseUserActivity::class.java)
                     intent.putExtra("imageURL", downloadUrl)
                     intent.putExtra("imageName", imageName)
                     intent.putExtra("message", messageEditText.text.toString())
                     startActivity(intent)
                 }

        }

    }
}
