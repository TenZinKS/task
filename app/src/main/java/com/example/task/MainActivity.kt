package com.example.task

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task.model.Book
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var editTextBookId: EditText
    private lateinit var editTextTitle: EditText
    private lateinit var editTextAuthor: EditText
    private lateinit var editTextYear: EditText
    private lateinit var buttonAddBook: Button
    private lateinit var buttonOpenLibrary: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance().reference.child("books")

        editTextBookId = findViewById(R.id.editTextBookId)
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextAuthor = findViewById(R.id.editTextAuthor)
        editTextYear = findViewById(R.id.editTextYear)
        buttonAddBook = findViewById(R.id.buttonAddBook)
        buttonOpenLibrary = findViewById(R.id.buttonOpenLibrary)

        buttonAddBook.setOnClickListener { addBook() }
        buttonOpenLibrary.setOnClickListener { openLibrary() }
    }

    private fun addBook() {
        val bookId = editTextBookId.text.toString()
        val title = editTextTitle.text.toString()
        val author = editTextAuthor.text.toString()
        val year = editTextYear.text.toString().toIntOrNull() ?: 0

        if (bookId.isEmpty() || title.isEmpty() || author.isEmpty() || year == 0) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val book = Book(bookId, title, author, year)
        database.child(bookId).setValue(book)
            .addOnSuccessListener {
                Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add book", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openLibrary() {
        startActivity(Intent(this, LibraryActivity::class.java))
    }

    private fun clearFields() {
        editTextBookId.text.clear()
        editTextTitle.text.clear()
        editTextAuthor.text.clear()
        editTextYear.text.clear()
    }
}
