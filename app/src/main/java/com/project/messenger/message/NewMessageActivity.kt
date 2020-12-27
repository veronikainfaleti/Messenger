package com.project.messenger.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.messenger.R
import com.project.messenger.model.UserNewMessage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select user"

        val recyclerView = findViewById<RecyclerView>(R.id.message_view_new_message)
        val adapter = GroupAdapter<GroupieViewHolder>()


        adapter.add(UserNewMessage())
        adapter.add(UserNewMessage())
        adapter.add(UserNewMessage())
        adapter.add(UserNewMessage())
        adapter.add(UserNewMessage())

        recyclerView.adapter = adapter
    }
}