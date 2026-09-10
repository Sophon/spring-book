package io.github.sophon.spring_book.util

internal fun String.equalsIgnoreCase(string: String?): Boolean {
    return this.equals(string, ignoreCase = true)
}
