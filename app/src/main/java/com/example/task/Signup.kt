package com.example.task

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class Signup : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        mAuth = FirebaseAuth.getInstance()

        binding.button2.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val email = binding.editTextText4.text.toString().trim()
        val name = binding.editTextText3.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()
        val confirmPassword = binding.editTextTextPassword2.text.toString().trim()

        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Signup successful.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(this, "User already exists.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Signup failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
