package com.amazingbooks.application.com.amazingbooks.resource;

import com.amazingbooks.application.com.amazingbooks.dto.BookPutDTO;
import com.amazingbooks.application.com.amazingbooks.entity.Book;
import com.amazingbooks.application.com.amazingbooks.service.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookResources {

    /**
     * Testing APIs
     * curl -X PUT "http://localhost:8086/book/add" -H "Content-Type: application/json"  -d '{"isbn": "isbn001", "title": "Title1", "publishedDate": "2022-03-11", "totalCopies": 100, "issuedCopies": 20, "author": "Anand"}'
     * curl -X GET "http://localhost:8086/books/fetch"
     * curl -X PUT "http://localhost:8086/book/add" -H "Content-Type: application/json"  -d '{"isbn": "isbn002", "title": "Title2", "publishedDate": "2022-06-11", "totalCopies": 150, "issuedCopies": 20, "author": "Anand2"}'
     * curl -X PUT "http://localhost:8086/book/edit" -H "Content-Type: application/json"  -d '{"totalCopies": 2500, "issuedCopies": 590, "author": "Anand2"}'
     * curl -X PUT "http://localhost:8086/book/delete" -H "Content-Type: application/json"  -d '{"totalCopies": 2500, "issuedCopies": 590, "author": "Anand2"}'
     */

    private final Logger logger = LoggerFactory.getLogger(BookResources.class);

    @Autowired
    private BooksService booksService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the world of books!";
    }

    // fetch/add/edit/delete

    @GetMapping("/books/fetch")
    public List<Book> fetchBooks() {
        logger.info("/books/fetch called!");
        return booksService.getAllBooks();
    }

    @PutMapping(value = "/book/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addBook(@RequestBody BookPutDTO bookDto) {
        logger.info("/book/add: {}", bookDto);
        Book book = mapper.convertValue(bookDto, Book.class);
        boolean bookAdded = booksService.add(book);
        return "Book added = " + bookAdded + "\r";
    }

    @PutMapping(value = "/book/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String editBook(@RequestBody BookPutDTO bookDto) {
        logger.info("/book/edit: {}", bookDto);
        Book book = mapper.convertValue(bookDto, Book.class);
        booksService.update(book);
        return "Book updated\n";
    }

    @PutMapping(value = "/book/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteBook(@RequestBody BookPutDTO bookDto) {
        logger.info("/book/delete: {}", bookDto);
        Book book = mapper.convertValue(bookDto, Book.class);
        booksService.delete(book);
        return "Book deleted\n";
    }
}
