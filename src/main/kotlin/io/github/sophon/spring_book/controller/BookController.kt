package io.github.sophon.spring_book.controller

import io.github.sophon.spring_book.mapper.toDomain
import io.github.sophon.spring_book.model.Book
import io.github.sophon.spring_book.model.BookRequestDto
import io.github.sophon.spring_book.util.equalsIgnoreCase
import io.github.sophon.spring_book.util.generateId
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/books")
internal class BookController {
    private val bookMap: MutableMap<Long, Book> = generateBooks()
    private val mutex = Mutex()


    @GetMapping
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

    @GetMapping("/{id}")
    fun getBook(@PathVariable id: Long): Book? {
        val result = bookMap[id]

        return result ?: throwNotFound(id)
    }

    @PostMapping
    suspend fun addBook(
        @RequestBody bookRequestDto: BookRequestDto,
    ) {
        mutex.withLock {
            val id = bookMap.generateId()
            val book = bookRequestDto.toDomain(id)

            bookMap.putIfAbsent(book.id, book)
        }
    }

    @PutMapping("/{id}")
    suspend fun updateBook(
        @PathVariable id: Long,
        @RequestBody newBook: Book,
    ) {
        mutex.withLock {
            if (id !in bookMap) {
                throwNotFound(id)
            } else {
                bookMap[id] = newBook
            }
        }
    }

    @DeleteMapping("/{id}")
    suspend fun deleteBook(
        @PathVariable id: Long,
    ) {
        mutex.withLock {
            if (id !in bookMap) {
                throwNotFound(id)
            } else {
                bookMap.remove(id)
            }
        }
    }


    private fun generateBooks(): MutableMap<Long, Book> {
        val books = listOf(
            Book(1, "Pride and Prejudice", "Jane Austen", "Romance"),
            Book(2, "1984", "George Orwell", "Dystopian"),
            Book(3, "To Kill a Mockingbird", "Harper Lee", "Fiction"),
            Book(4, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction"),
            Book(5, "Moby-Dick", "Herman Melville", "Adventure"),
            Book(6, "War and Peace", "Leo Tolstoy", "Historical"),
            Book(7, "Crime and Punishment", "Fyodor Dostoevsky", "Psychological"),
            Book(8, "The Brothers Karamazov", "Fyodor Dostoevsky", "Philosophical"),
            Book(9, "Anna Karenina", "Leo Tolstoy", "Romance"),
            Book(10, "Wuthering Heights", "Emily Brontë", "Gothic"),
            Book(11, "Jane Eyre", "Charlotte Brontë", "Gothic"),
            Book(12, "The Odyssey", "Homer", "Epic"),
            Book(13, "The Iliad", "Homer", "Epic"),
            Book(14, "Don Quixote", "Miguel de Cervantes", "Adventure"),
            Book(15, "Les Misérables", "Victor Hugo", "Historical"),
            Book(16, "The Count of Monte Cristo", "Alexandre Dumas", "Adventure"),
            Book(17, "Great Expectations", "Charles Dickens", "Fiction"),
            Book(18, "A Tale of Two Cities", "Charles Dickens", "Historical"),
            Book(19, "The Adventures of Huckleberry Finn", "Mark Twain", "Adventure"),
            Book(20, "Frankenstein", "Mary Shelley", "Gothic"),
        )
            .associateBy { it.id }
            .toMutableMap()
        return books
    }

    private fun throwNotFound(id: Long): Nothing {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book $id not found.")
    }
}
