package com.project.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailLogin = findViewById<EditText>(R.id.email_login).text
        val passwordLogin = findViewById<EditText>(R.id.password_login).text


        // Initialize Firebase Auth
        auth = Firebase.auth

        fun debug() {
            Log.d("LogIn", "E-mail: $emailLogin.")
            Log.d("LogIn", "Password: $passwordLogin.")
        }

        val loginButton = findViewById<Button>(R.id.login_button_login)!!
        loginButton.setOnClickListener {
            debug()
        }

        val register = findViewById<TextView>(R.id.login_to_register)
        register.setOnClickListener {
            Log.d("Log in", "Trying to register")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        Toast.makeText(applicationContext, "User authentication completed successfully!", Toast.LENGTH_SHORT).show()
    }
}