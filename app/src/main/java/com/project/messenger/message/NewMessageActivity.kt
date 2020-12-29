package com.project.messenger.message

import android.content.Intent
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
import com.xwray.groupie.Item

class NewMessageActivity : AppCompatActivity() {
    lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select user"

        val recyclerView = findViewById<RecyclerView>(R.id.message_view_new_message)

        getUsers(recyclerView)
    }

    private fun getUsers(view: RecyclerView) {
        val reference = FirebaseDatabase.getInstance().getReference("/users")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                groupAdapter = GroupAdapter()

                snapshot.children.forEach {

                    //needed to add a no-argument constructor for a deserializer
                    val user = it.getValue(User::class.java) ?: return

                    groupAdapter.add(UserNewMessage(user))
                    view.adapter = groupAdapter

                    //some debugging
                    Log.d(getString(R.string.TagNewMessageActivity), it.toString())

                    groupAdapter.setOnItemClickListener { item, view ->
                        toChat(
                            this@NewMessageActivity,
                            item,
                            view
                        )
                    }
                }
            }

        })
    }

    //transition to chat activity
    private fun toChat(
        activity: NewMessageActivity,
        item: Item<GroupieViewHolder>,
        view: View
    ) {
        val itemUser = item as UserNewMessage
        val intent = Intent(view.context, ChatActivity::class.java)
        intent.putExtra(userKey, itemUser.user) //added parcelable
        activity.startActivity(intent)
        finish()
    }

    companion object {
        const val userKey = "hello_from_the_other_side"
    }
}