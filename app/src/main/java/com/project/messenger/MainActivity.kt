package com.project.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = findViewById<EditText>(R.id.email_registration).text
        val password = findViewById<EditText>(R.id.password_registration).text


        fun debug() {
            Log.d(getString(R.string.TagRegister), "E-mail: $email.")
            Log.d(getString(R.string.TagRegister), "Password: $password.")
        }

        val logIn = findViewById<TextView>(R.id.log_in_from_register)
        logIn.setOnClickListener {
            Log.d(getString(R.string.TagRegister), "Trying to log in")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registrationButton = findViewById<Button>(R.id.button_register)!!
        registrationButton.setOnClickListener {
            debug()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                email.toString(),
                password.toString()
            ).addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    Log.d(
                        getString(R.string.TagRegister), "New user had been registered.")
                    return@addOnCompleteListener
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(getString(R.string.TagRegister), "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }




        }

    }



}