package com.example.levanmidterm1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.example.levanmidterm1.adapters.ChooseUserListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_choose_user.*

class ChooseUserActivity : AppCompatActivity() {

    var emails: ArrayList<String> = ArrayList()
    var keys: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)

        val adapter = ChooseUserListAdapter(this, emails)
        chooseUserListView.adapter = adapter

        FirebaseDatabase.getInstance().reference.child("users")
            .addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val email = p0.child("email").value as String
                    emails.add(email)
                    keys.add(p0.key.toString())
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
                override fun onChildRemoved(p0: DataSnapshot) {}

            })

        chooseUserListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val messageMap: Map<String, String> =
                mapOf("from" to FirebaseAuth.getInstance().currentUser!!.email.toString(),
                    "imageName" to intent.getStringExtra("imageName"),
                    "imageURL" to intent.getStringExtra("imageURL"),
                    "message" to intent.getStringExtra("message"))

            FirebaseDatabase.getInstance().reference
                .child("users").child(keys[position])
                .child("messages").push().setValue(messageMap)

            val intent = Intent(this, SecondActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}
