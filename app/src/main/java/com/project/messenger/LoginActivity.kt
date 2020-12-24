package com.project.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailLogin = findViewById<EditText>(R.id.email_login).text
        val passwordLogin = findViewById<EditText>(R.id.password_login).text


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
}