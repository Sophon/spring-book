package io.github.sophon.spring_book.util

internal fun Map<Long, *>.generateId(): Long {
    val id = generateSequence(
        seed = 1L,
        nextFunction = { it + 1 }
    ).first { this.containsKey(it).not() }
    return id
}