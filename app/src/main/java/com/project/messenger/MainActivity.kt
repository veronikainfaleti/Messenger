package com.project.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = findViewById<EditText>(R.id.email_registration).text
        val password = findViewById<EditText>(R.id.password_registration).text


        fun debug() {
            Log.d("Register", "E-mail: $email.")
            Log.d("Register", "Password: $password.")
        }

        val registrationButton = findViewById<Button>(R.id.button_register)!!
        registrationButton.setOnClickListener {
            debug()
        }

        val logIn = findViewById<TextView>(R.id.log_in_from_register)
        logIn.setOnClickListener {
            Log.d("Register", "Trying to log in")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

}