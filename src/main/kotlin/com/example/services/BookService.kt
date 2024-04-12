package com.example.services

import com.example.models.Book;

class BookService {
    private val books = mutableListOf<Book>();
    init {
        books.add(Book(1,"Наталка Полтавка","Іван Котляревский","1798"))
        books.add(Book(2,"Тарас Бульба","Микола Гоголь","1835"))
        books.add(Book(3,"Маруся Чурай","Григорій Квітка-Основ'яненко","1834"))
        books.add(Book(4,"Кайдашева сім'я","Іван Нечуй-Левицький","1878"))
        books.add(Book(5,"Чорна рада","Микола Янчук","1841"))
    }

    public fun getBooks() : List<Book> = books

    public fun getBookById(id: Int?) : Book? {
        return books.find { book -> book.id == id }
    }

    public fun addBook(book: Book) : Book? {
        books.add(book)
        return book
    }

    public fun deleteBook(id: Int?) {
        books.removeAll{ book -> book.id == id }
    }

    public fun updateBook(id: Int?, book: Book) : Book? {
        val index = books.indexOfFirst { book -> book.id == id }
        if (index != -1) {
            books[index] = book
            return book
        }
        return null
    }

    public fun getBookByTitle(title: String) : Book? {
        return books.find { book -> book.title == title }
    }
}