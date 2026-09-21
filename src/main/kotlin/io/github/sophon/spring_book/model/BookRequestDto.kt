package io.github.sophon.spring_book.model

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

internal data class BookRequestDto(
    @Size(min = 1, max = 30, message = "Must be 1-30 characters")
    val title: String,

    @Size(min = 1, max = 40, message = "Must be 1-40 characters")
    val author: String,

    @Size(min = 1, max = 30, message = "Must be 1-30 characters")
    val category: String,

    @Min(value = 1, message = "Rating must be 1-5")
    @Max(value = 5, message = "Rating must be 1-5")
    val rating: Int? = null,
)
