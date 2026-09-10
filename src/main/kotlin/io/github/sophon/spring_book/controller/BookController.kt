package io.github.sophon.spring_book.controller

import io.github.sophon.spring_book.Book
import io.github.sophon.spring_book.util.equalsIgnoreCase
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
internal class BookController {
    private val bookMap: MutableMap<String, Book> = generateBooks()
    private val mutex = Mutex()


    @GetMapping("/api/books")
    fun getBooks(
        @RequestParam(required = false) category: String?,
    ): List<Book> {
        if (category.isNullOrBlank()) {
            return bookMap.values.toList()
        }

        val result = bookMap.values
            .filter { it.category.equalsIgnoreCase(category) }

        return result
    }

    @GetMapping("/api/books/{title}")
    fun getBook(@PathVariable title: String): Book? {
        return bookMap[title]
    }

    @PostMapping("/api/books")
    suspend fun addBook(
        @RequestBody book: Book,
    ) {
        mutex.withLock {
            bookMap.putIfAbsent(book.title, book)
        }
    }

    @PutMapping("/api/books/{title}")
    suspend fun updateBook(
        @PathVariable title: String,
        @RequestBody newBook: Book,
    ) {
        mutex.withLock {
            if (title !in bookMap) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book $title not found.")
            } else {
                bookMap[title] = newBook
            }
        }
    }


    private fun generateBooks(): MutableMap<String, Book> {
        val books = listOf(
            Book("Pride and Prejudice", "Jane Austen", "Romance"),
            Book("1984", "George Orwell", "Dystopian"),
            Book("To Kill a Mockingbird", "Harper Lee", "Fiction"),
            Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction"),
            Book("Moby-Dick", "Herman Melville", "Adventure"),
            Book("War and Peace", "Leo Tolstoy", "Historical"),
            Book("Crime and Punishment", "Fyodor Dostoevsky", "Psychological"),
            Book("The Brothers Karamazov", "Fyodor Dostoevsky", "Philosophical"),
            Book("Anna Karenina", "Leo Tolstoy", "Romance"),
            Book("Wuthering Heights", "Emily Brontë", "Gothic"),
            Book("Jane Eyre", "Charlotte Brontë", "Gothic"),
            Book("The Odyssey", "Homer", "Epic"),
            Book("The Iliad", "Homer", "Epic"),
            Book("Don Quixote", "Miguel de Cervantes", "Adventure"),
            Book("Les Misérables", "Victor Hugo", "Historical"),
            Book("The Count of Monte Cristo", "Alexandre Dumas", "Adventure"),
            Book("Great Expectations", "Charles Dickens", "Fiction"),
            Book("A Tale of Two Cities", "Charles Dickens", "Historical"),
            Book("The Adventures of Huckleberry Finn", "Mark Twain", "Adventure"),
            Book("Frankenstein", "Mary Shelley", "Gothic"),
        )
            .associateBy { it.title }
            .toMutableMap()
        return books
    }
}
