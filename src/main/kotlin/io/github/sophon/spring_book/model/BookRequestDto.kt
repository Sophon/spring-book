package io.github.sophon.spring_book.model

internal data class BookRequestDto(
    val title: String,
    val author: String,
    val category: String,
    val rating: Int? = null,
)
