package io.github.sophon.spring_book

internal data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val category: String,
    val rating: Int? = null,
)
