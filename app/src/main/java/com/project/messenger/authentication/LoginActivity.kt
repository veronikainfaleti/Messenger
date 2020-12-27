package com.project.messenger.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.messenger.R
import com.project.messenger.message.LatestMessagesActivity

class LoginActivity : AppCompatActivity() {
    /*lateinit var emailLogin: Editable
    lateinit var passwordLogin: Editable*/
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val loginButton = findViewById<Button>(R.id.login_button_login)!!
        loginButton.setOnClickListener {
            login()
        }

        val register = findViewById<TextView>(R.id.login_to_register)
        register.setOnClickListener {
            Log.d(getString(R.string.loginTag), "Trying to register a new user...")

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val emailLogin = findViewById<EditText>(R.id.email_login).text
        val passwordLogin = findViewById<EditText>(R.id.password_login).text

        Log.d(getString(R.string.loginTag), "E-mail: $emailLogin.")
        Log.d(getString(R.string.loginTag), "Password: $passwordLogin.")

        auth.signInWithEmailAndPassword(
            emailLogin.toString(),
            passwordLogin.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                Log.d(getString(R.string.loginTag), "Successfully logged in.")
                toLatestMessage(this)
            } else {
                Log.w(getString(R.string.loginTag), "Cannot perform login.", task.exception)
                Toast.makeText(
                    this,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    companion object {
        private fun toLatestMessage(activity: LoginActivity) {
            val intent = Intent(activity, LatestMessagesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }
    }


}