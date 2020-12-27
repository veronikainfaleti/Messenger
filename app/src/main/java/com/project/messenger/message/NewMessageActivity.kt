package com.project.messenger.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.messenger.R
import com.project.messenger.model.User
import com.project.messenger.model.UserNewMessage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select user"

        val recyclerView = findViewById<RecyclerView>(R.id.message_view_new_message)
        
        getUsers(recyclerView)
    }

    private fun getUsers(view: RecyclerView) {
        val reference = FirebaseDatabase.getInstance().getReference("/users")
        reference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                //TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val groupAdapter = GroupAdapter<GroupieViewHolder>()

                snapshot.children.forEach {
                    val user = it.getValue(User::class.java) ?: return
                    groupAdapter.add(UserNewMessage(user))
                    view.adapter = groupAdapter
                    Log.d(getString(R.string.TagNewMessageActivity), it.toString())

                }
            }

        })
    }
}