package io.github.sophon.spring_book.mapper

import io.github.sophon.spring_book.model.Book
import io.github.sophon.spring_book.model.BookRequestDto

internal fun BookRequestDto.toDomain(id: Long): Book {
    val book = Book(
        id = id,
        title = this.title,
        author = this.author,
        category = this.category,
        rating = this.rating
    )
    return book
}
