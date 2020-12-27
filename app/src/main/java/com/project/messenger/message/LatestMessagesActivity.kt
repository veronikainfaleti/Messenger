package com.project.messenger.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.project.messenger.R
import com.project.messenger.authentication.LoginActivity

class LatestMessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        verification()
    }

    private fun verification() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) toLogin(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_message_exit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.new_message_menu -> {
                toNewMessage(this)
            }
            R.id.exit_menu -> {
                FirebaseAuth.getInstance().signOut()
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