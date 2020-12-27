package com.project.messenger.model

import android.widget.TextView
import com.project.messenger.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class UserNewMessage(private val user: User) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.last_message
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.new_message_username_latest_messages).text =
            user.username
    }

}