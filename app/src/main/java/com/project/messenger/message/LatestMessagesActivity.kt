package com.project.messenger.message

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.project.messenger.R
import com.project.messenger.authentication.LoginActivity
import com.project.messenger.model.Message
import com.project.messenger.model.MessageLatestMessages
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.time.LocalDateTime

class LatestMessagesActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        verification()

        recyclerView = findViewById(R.id.list_latest_messages)
        groupAdapter = GroupAdapter()
        recyclerView.adapter = groupAdapter

        getLatestMessages()

        groupAdapter.setOnItemClickListener { item, _ ->
            val intent = Intent(this, ChatActivity::class.java)
            val itemMessage = item as MessageLatestMessages
            intent.putExtra(NewMessageActivity.userKey, itemMessage.user)
            startActivity(intent)
        }
    }


    private val messages = LinkedHashMap<String, Message>()
    private fun refresh() {
        groupAdapter.clear()
        val sortedMessages = messages.values.sortedByDescending { it.doubleTimeSource }
        for (message in sortedMessages) {
            groupAdapter.add(MessageLatestMessages(message))
        }
    }
    private fun getMessage(snapshot: DataSnapshot) {
        val message = snapshot.getValue(Message::class.java) ?: return
        messages[snapshot.key!!] = message
        refresh()
    }

    private fun getLatestMessages() {
        val currentUserID = auth.uid
        val reference = FirebaseDatabase.getInstance().getReference("/last-message/$currentUserID")
        reference.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                getMessage(snapshot)
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                getMessage(snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

        })
    }

    private fun verification() {
        auth = Firebase.auth
        val uid = auth.uid
        if (uid == null) toLogin(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_message_exit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.new_message_menu -> toNewMessage(this)
            R.id.exit_menu -> {
                auth.signOut()
                toLogin(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private fun toLogin(latestMessagesActivity: LatestMessagesActivity) {
            val intent = Intent(latestMessagesActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            latestMessagesActivity.startActivity(intent)
        }

        private fun toNewMessage(latestMessagesActivity: LatestMessagesActivity) {
            val intent = Intent(latestMessagesActivity, NewMessageActivity::class.java)
            latestMessagesActivity.startActivity(intent)
        }
    }
}