package com.project.messenger.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.project.messenger.R
import com.project.messenger.model.NewMessageMessagesFrom
import com.project.messenger.model.NewMessageMessagesTo
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.title = "Username"

        val recyclerView = findViewById<RecyclerView>(R.id.message_view_chat)
        val groupAdapter = GroupAdapter<GroupieViewHolder>()

        groupAdapter.add(NewMessageMessagesFrom())
        groupAdapter.add(NewMessageMessagesTo())
        groupAdapter.add(NewMessageMessagesTo())
        groupAdapter.add(NewMessageMessagesFrom())
        groupAdapter.add(NewMessageMessagesFrom())
        groupAdapter.add(NewMessageMessagesTo())
        groupAdapter.add(NewMessageMessagesTo())
        groupAdapter.add(NewMessageMessagesFrom())
        groupAdapter.add(NewMessageMessagesFrom())
        groupAdapter.add(NewMessageMessagesTo())
        groupAdapter.add(NewMessageMessagesTo())
        groupAdapter.add(NewMessageMessagesFrom())

        recyclerView.adapter = groupAdapter
    }
}