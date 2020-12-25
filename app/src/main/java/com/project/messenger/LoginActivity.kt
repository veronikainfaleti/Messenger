package com.project.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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
    /*lateinit var emailLogin: Editable
    lateinit var passwordLogin: Editable*/
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val loginButton = findViewById<Button>(R.id.login_button_login)!!
        loginButton.setOnClickListener {
            val emailLogin = findViewById<EditText>(R.id.email_login).text
            val passwordLogin = findViewById<EditText>(R.id.password_login).text

            Log.d(getString(R.string.loginTag), "E-mail: $emailLogin.")
            Log.d(getString(R.string.loginTag), "Password: $passwordLogin.")

            auth.signInWithEmailAndPassword(
                emailLogin.toString(),
                passwordLogin.toString()
            ).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d(getString(R.string.loginTag), "Successfully logged in")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    Log.w(getString(R.string.loginTag), "Cannot perform log in", task.exception)
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }

            }
        }

        val register = findViewById<TextView>(R.id.login_to_register)
        register.setOnClickListener {
            Log.d(getString(R.string.loginTag), "Trying to register")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    /*public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        TODO("Not yet implemented")
    }*/

    /*private fun debug() {
        Log.d(getString(R.string.loginTag), "E-mail: $emailLogin.")
        Log.d(getString(R.string.loginTag), "Password: $passwordLogin.")
    }*/

}