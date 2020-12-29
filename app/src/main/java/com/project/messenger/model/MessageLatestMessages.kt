package com.project.messenger.model

import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.project.messenger.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class MessageLatestMessages(val message: Message): Item<GroupieViewHolder>() {
    lateinit var user: User
    private lateinit var auth: FirebaseAuth
    override fun getLayout(): Int {
        return R.layout.message_latest_messages
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        auth = Firebase.auth
        val messageText = viewHolder.itemView.findViewById<TextView>(R.id.message_text_latest_messages)
        messageText.text = message.message

        val partnerID = if (auth.uid == message.partner) message.currentUser else message.partner

        val reference = FirebaseDatabase.getInstance().getReference("/users/$partnerID")
        reference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                val partnerNameText = viewHolder.itemView.findViewById<TextView>(R.id.username_latest_messages)
                partnerNameText.text = user.username
            }

        })
    }
}