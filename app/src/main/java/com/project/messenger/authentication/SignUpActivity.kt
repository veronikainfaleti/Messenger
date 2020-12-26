package com.project.messenger.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.project.messenger.R
import com.project.messenger.model.User

class MainActivity : AppCompatActivity() {
    /*lateinit var name: Editable
    lateinit var email: Editable
    lateinit var password: Editable*/
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth

        val logIn = findViewById<TextView>(R.id.log_in_from_register)
        logIn.setOnClickListener {
            Log.d(getString(R.string.TagRegister), "Trying to log in")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registrationButton = findViewById<Button>(R.id.button_register)!!
        registrationButton.setOnClickListener {
            registration()
        }

    }

    private fun debug() {

    }

    private fun registration() {
        //debug()

        val name = findViewById<EditText>(R.id.username_registration).text
        val email = findViewById<EditText>(R.id.email_registration).text
        val password = findViewById<EditText>(R.id.password_registration).text

        if (name.isEmpty() or email.isEmpty() or password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Fields cannot be empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        Log.d(getString(R.string.TagRegister), "E-mail: $email.")
        Log.d(getString(R.string.TagRegister), "Password: $password.")

        auth.createUserWithEmailAndPassword(
            email.toString(),
            password.toString()
        ).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                Log.d(getString(R.string.TagRegister), "New user had been registered.")
                val user = auth.currentUser
                saveUserToDatabase(name.toString())
                //updateUI(user)
            } else {
                Log.w(getString(R.string.TagRegister), "Failed to create a new user.", task.exception)
                Toast.makeText(
                    this,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
                //updateUI(null)
            }
        }
    }

    private fun saveUserToDatabase(name: String) {
        val uid = auth.uid ?: return
        val reference = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, name)
        reference.setValue(user).addOnSuccessListener {
            Log.d(getString(R.string.TagRegister), "New user had been added to the database.")
        }.addOnFailureListener {
            Log.w(getString(R.string.TagRegister), "Failed to create a new user.", it)
        }
    }

}