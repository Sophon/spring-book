package io.github.sophon.spring_book.controller

import io.github.sophon.spring_book.Book
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class BookController {
    private val bookList: List<Book> = generateBooks()


    @GetMapping("/hello")
    fun hello(): String {
        return "Hello world"
    }

    @GetMapping("/api/books")
    fun getBooks(): List<Book> {
        return bookList
    }


    private fun generateBooks(): List<Book> {
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
        return books
    }
}
