package com.project.messenger.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.project.messenger.R
import com.project.messenger.model.Message
import com.project.messenger.model.NewMessageMessagesFrom
import com.project.messenger.model.NewMessageMessagesTo
import com.project.messenger.model.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ChatActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val chatPartner = intent.getParcelableExtra<User>(NewMessageActivity.userKey)
        supportActionBar?.title = chatPartner?.username


        recyclerView = findViewById(R.id.message_view_chat)
        groupAdapter = GroupAdapter()

        recyclerView.adapter = groupAdapter

        auth = Firebase.auth
        getMessages(chatPartner)

        val buttonSend = findViewById<Button>(R.id.send_message_chat)
        val messageTextView = findViewById<TextView>(R.id.message_to_chat_new)

        buttonSend.setOnClickListener {
            sendMessage(chatPartner)
        }
    }

    private fun getMessages(chatPartner: User?) {

        //val auth = Firebase.auth
        val currentUser = auth.currentUser ?: return
        val reference = FirebaseDatabase.getInstance()
            .getReference("/all-messages/${currentUser.uid}/${chatPartner!!.uid}")

        reference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                //needed to add a no-argument constructor for a deserializer
                val message = snapshot.getValue(Message::class.java) ?: return

                //some debugging
                Log.d(getString(R.string.chat_activity), message.toString())

                if (message.currentUser == auth.currentUser?.uid)
                    groupAdapter.add(
                        NewMessageMessagesFrom(message.message)
                    ) //refreshes by itself
                else
                    groupAdapter.add(
                        NewMessageMessagesTo(
                            message.message,
                            chatPartner.username
                        )
                    ) //refreshes by itself


            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

        })
    }

    private fun sendMessage(chatPartner: User?) {
        //get message block
        val messageText = findViewById<EditText>(R.id.text_message_chat)

        //get current user
        val currentUser = auth.currentUser ?: return

        //trying to learn how nodes in nosql work
        val reference = FirebaseDatabase.getInstance()
            .getReference("/all-messages/${currentUser.uid}/${chatPartner!!.uid}").push()

        //get message
        val message =
            Message(
                reference.key!!,
                currentUser.uid,
                chatPartner.uid,
                messageText.text.toString()
            )

        //inject message inside of the current node
        reference.setValue(message).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(
                    getString(R.string.chat_activity),
                    "New message is send successfully. Node: ${reference.key!!}"
                )
                messageText.text.clear()
            } else {
                Log.w(
                    getString(R.string.chat_activity),
                    "Failed to send a message: $currentUser -> $chatPartner",
                    it.exception
                )
            }
        }

        //if it is a dialog (and not a monologue) perform reverse reference injection
        if (currentUser.uid != chatPartner.uid) {
            val reverseReference = FirebaseDatabase.getInstance()
                .getReference("/all-messages/${chatPartner.uid}/${currentUser.uid}").push()

            reverseReference.setValue(message).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(
                        getString(R.string.chat_activity),
                        "New message is send successfully. Node: ${reverseReference.key!!}"
                    )
                } else {
                    Log.w(
                        getString(R.string.chat_activity),
                        "Failed to send a message: $chatPartner -> $currentUser",
                        it.exception
                    )
                }
            }
        }
    }
}