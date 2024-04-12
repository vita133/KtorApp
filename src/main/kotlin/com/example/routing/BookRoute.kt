package com.example.routing

import com.example.models.Book
import com.example.services.BookService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Exception

@Suppress("unused")
fun Route.bookRoutes() {

    val bookService = BookService()

    route("books") {
        get {
            call.respond(bookService.getBooks())
        }

        get("/{id}") {
            val bookIdFromQuery = call.parameters["id"] ?: kotlin.run {
                throw Exception("Please provide a valid id")
            }
            val book = bookService.getBookById(bookIdFromQuery?.toIntOrNull())
            if (book == null) {
                call.respond(HttpStatusCode.NotFound, "Book not found");
            } else {
                call.respond(book)
            }
        }

        post {
            val requestBody = call.receive<Book>()
            val existingBook = bookService.getBookById(requestBody.id)
            if (existingBook != null) {
                call.respond(HttpStatusCode.Conflict, "Book with id ${requestBody.id} already exists")
            } else {
                bookService.addBook(requestBody)
                call.respond(HttpStatusCode.Created, requestBody)
            }
        }

        delete("/{id}") {
            val bookIdFromQuery = call.parameters["id"] ?: kotlin.run {
                throw Exception("Please provide a valid id")
            }
            bookService.deleteBook(bookIdFromQuery?.toIntOrNull())
            call.respond("Book is deleted")
        }

        put("/{id}") {
            val bookIdFromQuery = call.parameters["id"] ?: kotlin.run {
                throw Exception("Please provide a valid id")
            }
            val requestBody = call.receive<Book>()
            val book = bookService.updateBook(bookIdFromQuery?.toIntOrNull(), requestBody)
            if (book == null) {
                call.respond(HttpStatusCode.NotFound, "Book not found");
            } else {
                call.respond(book)
            }
        }

        get("/title/{title}") {
            val bookTitleFromQuery = call.parameters["title"] ?: kotlin.run {
                throw Exception("Please provide a valid title")
            }
            val book = bookService.getBookByTitle(bookTitleFromQuery)
            if (book == null) {
                call.respond(HttpStatusCode.NotFound, "Book not found");
            } else {
                call.respond(book)
            }
        }
    }
}