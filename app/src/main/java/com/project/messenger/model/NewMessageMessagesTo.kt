package com.project.messenger.model

import android.widget.TextView
import com.project.messenger.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class NewMessageMessagesTo(val message: String, val name: String) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.to_user_message
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val textView = viewHolder.itemView.findViewById<TextView>(R.id.message_to_chat_new)
        textView.text = message
        val nameView = viewHolder.itemView.findViewById<TextView>(R.id.name_to_chat)
        nameView.text = name
    }
}