package com.project.messenger.model

import android.widget.TextView
import com.project.messenger.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class NewMessageMessagesFrom(val message: String) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.from_user_message
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val textView = viewHolder.itemView.findViewById<TextView>(R.id.message_from_chat_new)
        textView.text = message
    }
}