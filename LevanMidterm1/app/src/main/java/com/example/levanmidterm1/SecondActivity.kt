package com.example.levanmidterm1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import com.example.levanmidterm1.adapters.ChooseUserListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var emails: ArrayList<String> = ArrayList()
    var messages: ArrayList<DataSnapshot> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        auth = Firebase.auth

        val adapter = ChooseUserListAdapter(this, emails)
        myMessagesListView.adapter = adapter

        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(auth.currentUser?.uid.toString())
            .child("messages").addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    emails.add(p0.child("from").value as String)
                    messages.add(p0)
                    adapter.notifyDataSetChanged()
                }
                override fun onCancelled(p0: DatabaseError) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
                override fun onChildRemoved(p0: DataSnapshot) {}

            })

        myMessagesListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val snapshot = messages[position]
                val intent = Intent(this, ViewMessageActivity::class.java)
                intent.putExtra("imageName", snapshot.child("imageName").value as String)
                intent.putExtra("imageURL", snapshot.child("imageURL").value as String )
                intent.putExtra("message", snapshot.child("message").value as String)
                intent.putExtra("imageName", snapshot.key)
                startActivity(intent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.createMessage) {
            val intent = Intent(this, CreateMessageActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.logout) {
            auth.signOut()
            finish()
        } else if (item.itemId == R.id.covid19Stats) {
            val intent = Intent(this, Covid19Activity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        auth.signOut()
    }
}
