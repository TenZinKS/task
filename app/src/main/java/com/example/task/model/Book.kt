package com.example.task.model

data class Book(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val year: Int = 0
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "author" to author,
            "year" to year
        )
    }
}