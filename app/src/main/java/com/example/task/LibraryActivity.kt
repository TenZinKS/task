package com.example.task

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.adapter.BookAdapter
import com.example.task.databinding.ActivityLibraryBinding
import com.example.task.model.Book
import com.google.firebase.database.*

class LibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLibraryBinding
    private lateinit var database: DatabaseReference
    private lateinit var bookList: MutableList<Book>
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference.child("books")
        bookList = mutableListOf()
        bookAdapter = BookAdapter(bookList, this::onBookClick)

        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBooks.adapter = bookAdapter

        binding.buttonLoadBooks.setOnClickListener { loadBooks() }

        binding.buttonUpdateBook.setOnClickListener { updateBook() }

        binding.buttonDeleteBook.setOnClickListener { deleteBook() }

        loadBooks()
    }

    private fun loadBooks() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookList.clear()
                for (data in snapshot.children) {
                    val book = data.getValue(Book::class.java)
                    if (book != null) {
                        bookList.add(book)
                    }
                }
                bookAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LibraryActivity, "Failed to load books", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onBookClick(book: Book) {
        binding.editTextBookId.setText(book.id)
        binding.editTextTitle.setText(book.title)
        binding.editTextAuthor.setText(book.author)
        binding.editTextYear.setText(book.year.toString())
    }

    private fun updateBook() {
        val bookId = binding.editTextBookId.text.toString()
        val title = binding.editTextTitle.text.toString()
        val author = binding.editTextAuthor.text.toString()
        val year = binding.editTextYear.text.toString().toIntOrNull() ?: 0

        if (bookId.isEmpty() || title.isEmpty() || author.isEmpty() || year == 0) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val book = Book(bookId, title, author, year)
        database.child(bookId).updateChildren(book.toMap())
            .addOnSuccessListener {
                Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteBook() {
        val bookId = binding.editTextBookId.text.toString()

        if (bookId.isEmpty()) {
            Toast.makeText(this, "Please enter book ID", Toast.LENGTH_SHORT).show()
            return
        }

        database.child(bookId).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to delete book", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearFields() {
        binding.editTextBookId.text.clear()
        binding.editTextTitle.text.clear()
        binding.editTextAuthor.text.clear()
        binding.editTextYear.text.clear()
    }
}
