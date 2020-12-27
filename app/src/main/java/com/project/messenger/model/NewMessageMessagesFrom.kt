package com.project.messenger.model

import com.project.messenger.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class NewMessageMessagesFrom : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.from_user_message
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }
}