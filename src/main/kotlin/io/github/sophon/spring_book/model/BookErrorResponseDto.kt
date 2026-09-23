package io.github.sophon.spring_book.model

internal data class BookErrorResponseDto(
    val status: Int,
    val message: String = "",
    val timeStamp: Long,
)
