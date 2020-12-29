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

        buttonSend.setOnClickListener {
            sendMessage(chatPartner)
        }
    }

    private fun getMessages(chatPartner: User?) {
        val currentUser = auth.currentUser ?: return
        chatPartner ?: return
        val reference = FirebaseDatabase.getInstance()
            .getReference("/all-messages/${currentUser.uid}/${chatPartner.uid}")

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

        if (chatPartner == null) return

        //trying to learn how nodes in nosql work
        //we are pushing a new message into the new node inside of the /all-messages/../../ node
        val referenceAllMessages = FirebaseDatabase.getInstance()
            .getReference("/all-messages/${currentUser.uid}/${chatPartner.uid}").push()

        //we are just changing the current node if it exists
        //or we create a new node if it is first message in the dialog
        val referenceLastMessage = FirebaseDatabase.getInstance()
            .getReference("/last-message/${currentUser.uid}/${chatPartner.uid}")


        //get message
        val message =
            Message(
                referenceAllMessages.key!!,
                currentUser.uid,
                chatPartner.uid,
                messageText.text.toString(),
                System.currentTimeMillis().toString()
            )

        //inject message inside of the current node
        referenceAllMessages.setValue(message).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(
                    getString(R.string.chat_activity),
                    "New message is send successfully. Node: ${referenceAllMessages.key!!}\n" +
                            "message: $message"
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

        //reference for the last message node
        referenceLastMessage.setValue(message).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(
                    getString(R.string.chat_activity),
                    "Last message is saved successfully. Node: ${referenceLastMessage.key!!}"
                )
            } else {
                Log.w(
                    getString(R.string.chat_activity),
                    "Failed to save the last message: $currentUser -> $chatPartner",
                    it.exception
                )
            }
        }

        //if it is a dialog (and not a monologue) perform reverse reference injection
        if (currentUser.uid != chatPartner.uid) {
            val reverseReferenceAllMessages = FirebaseDatabase.getInstance()
                .getReference("/all-messages/${chatPartner.uid}/${currentUser.uid}").push()

            val reverseReferenceLastMessage = FirebaseDatabase.getInstance()
                .getReference("/last-message/${chatPartner.uid}/${currentUser.uid}")

            reverseReferenceAllMessages.setValue(message).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(
                        getString(R.string.chat_activity),
                        "New message is send successfully. Node: ${reverseReferenceAllMessages.key!!}"
                    )
                } else {
                    Log.w(
                        getString(R.string.chat_activity),
                        "Failed to send a message: $chatPartner -> $currentUser",
                        it.exception
                    )
                }
            }


            //reverse reference for the last message node
            reverseReferenceLastMessage.setValue(message).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(
                        getString(R.string.chat_activity),
                        "Last message is saved successfully. Node: ${reverseReferenceLastMessage.key!!}"
                    )
                } else {
                    Log.w(
                        getString(R.string.chat_activity),
                        "Failed to save the last message: $chatPartner -> $currentUser",
                        it.exception
                    )
                }
            }
        }
    }
}