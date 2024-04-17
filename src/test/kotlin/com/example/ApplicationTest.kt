package com.example

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import com.example.models.Book
import io.ktor.server.config.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.*

class ApplicationTest {

//    @Test
//    fun testHello() = testApplication {
//        environment {
//            config = ApplicationConfig("application-custom.conf")
//        }
//    }

//    @Test
//    fun testDevEnvironment() = testApplication {
//        environment {
//            config = MapApplicationConfig("ktor.environment" to "dev")
//        }
//    }

    @Test
    fun testRoot() = testApplication {
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello World!", response.bodyAsText())
    }

    @Test
    fun testBooksRoot() = testApplication {
        val response = client.get("/books")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testGetBookById() = testApplication {
        val bookId = 1
        val response = client.get("/books/$bookId")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testGetBookByIdNotFound() = testApplication {
        val bookId = 13400000
        val response = client.get("/books/$bookId")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Book not found", response.bodyAsText())
    }

    @Test
    fun testPostBook() = testApplication {
        val response = client.post("/books") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(Book(100, "title", "author", "2024")))
        }
        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun testPostBookConflict() = testApplication {
        val id = 1
        val response = client.post("/books") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(Book(id, "title", "author", "2024")))
        }
        assertEquals(HttpStatusCode.Conflict, response.status)
        assertEquals("Book with id ${id} already exists", response.bodyAsText())
    }

    @Test
    fun testDeleteBookById() = testApplication {
        val bookId = 1
        val response = client.delete("/books/$bookId")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Book is deleted", response.bodyAsText())
    }

    @Test
    fun testDeleteBookByIdBadRequest() = testApplication {
        val bookId = 10000
        val response = client.delete("/books/$bookId")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Book not found", response.bodyAsText())
    }

    @Test
    fun testUpdateBookById() = testApplication {
        val bookId = 1
        val response = client.put("/books/$bookId") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(Book(bookId, "title", "author", "2024")))
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testUpdateBookByIdNotFound() = testApplication {
        val bookId = 10000
        val response = client.put("/books/$bookId") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(Book(bookId, "title", "author", "2024")))
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Book not found", response.bodyAsText())
    }

    @Test
    fun testGetBookByTitle() = testApplication {
        val bookTitle = "Наталка Полтавка"
        val response = client.get("/books/title/$bookTitle")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testGetBookByTitleNotFound() = testApplication {
        val bookTitle = "Н"
        val response = client.get("/books/title/$bookTitle")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("Book not found", response.bodyAsText())
    }
}
