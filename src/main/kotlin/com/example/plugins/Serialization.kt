package com.example.plugins

import com.example.models.Book
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
//    val books = mutableListOf<Book>()
    routing {
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
//        post("/book") {
//            val requestBody = call.receive<Book>()
//            books.add(requestBody)
//            call.respond(requestBody)
//        }
//
//        get("/books") {
//            call.respond(books)
//        }
    }
}