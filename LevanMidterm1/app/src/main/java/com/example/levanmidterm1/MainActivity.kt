package com.example.levanmidterm1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goButton.setOnClickListener {
            goClicked(it)
        }

        auth = Firebase.auth

        if (auth.currentUser != null) {
            logIn()
        }

    }

    private fun goClicked(view: View) {
        auth.signInWithEmailAndPassword(emailEditText.text.toString(),
            passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    auth.createUserWithEmailAndPassword(emailEditText.text.toString(),
                        passwordEditText.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                database = Firebase.database.reference
                                database.child("users")
                                    .child(task.result?.user?.uid.toString())
                                    .child("email").setValue(emailEditText.text.toString())
//                                FirebaseDatabase.getInstance()
//                                    .reference.child("users")
//                                    .child(task.result?.user?.uid.toString())
//                                    .child("email").setValue(emailEditText.text.toString())
                                logIn()
                            } else {
                                Toast.makeText(this, "Login has failed! Try Again!",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }

    }

    private fun logIn() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
}
