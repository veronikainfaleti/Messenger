package com.project.messenger.model

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import com.project.messenger.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class UserNewMessage() : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.last_message
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //TODO("Not yet implemented")
    }

}