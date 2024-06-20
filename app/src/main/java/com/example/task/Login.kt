package com.example.task

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException


class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        mAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            loginUser()
        }

        binding.button3.setOnClickListener {
            startActivity(Intent(this,Signup::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.editTextText.text.toString().trim()
        val password = binding.editTextText2.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
