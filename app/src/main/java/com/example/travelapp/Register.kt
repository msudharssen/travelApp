package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var progress: ProgressBar
    private lateinit var editEmail: TextInputEditText
    private lateinit var editPassword: TextInputEditText
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize UI components
        editEmail = findViewById(R.id.email)
        editPassword = findViewById(R.id.password)
        buttonRegister = findViewById(R.id.btnLogIn)
        progress = findViewById(R.id.progressbar)
        textViewLogin = findViewById(R.id.loginNow)

        textViewLogin.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        buttonRegister.setOnClickListener {
            progress.visibility = View.VISIBLE
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            if (email.isEmpty()) {
                progress.visibility = View.GONE
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                progress.visibility = View.GONE
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progress.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Authentication Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Authentication unsuccessful: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        println("Authentication unsuccessful: ${task.exception?.message}")
                    }
                }
        }
    }
}
