package com.example.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.Book

class BookAdapter(
    private val books: List<Book>,
    private val onBookClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        val textViewYear: TextView = itemView.findViewById(R.id.textViewYear)

        fun bind(book: Book) {
            textViewTitle.text = book.title
            textViewAuthor.text = book.author
            textViewYear.text = book.year.toString()
            itemView.setOnClickListener { onBookClick(book) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size
}