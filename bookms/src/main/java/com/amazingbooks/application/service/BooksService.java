package com.amazingbooks.application.service;

import com.amazingbooks.application.entity.Book;
import com.amazingbooks.application.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public boolean add(Book book) {
        bookRepository.save(book);
        return true;
    }

    public void update(Book book) {
        Book bookInRepo = findBookInRepo(book);
        bookInRepo.setIssuedCopies(book.getIssuedCopies() == 0 ? bookInRepo.getIssuedCopies() : book.getIssuedCopies());
        bookInRepo.setTotalCopies(book.getTotalCopies() == 0 ? bookInRepo.getTotalCopies() : book.getTotalCopies());
        bookRepository.save(bookInRepo);
    }

    public void delete(Book book) {
        Book bookInRepo = findBookInRepo(book);
        bookRepository.delete(bookInRepo);
    }

    Book findBookInRepo(Book book) {
        Book bookInRepo = new Book();
        for (Book eachBook : bookRepository.findAll()) {
            if (eachBook.getAuthor().equals(book.getAuthor())) {
                bookInRepo = eachBook;
            }
        }
        return bookInRepo;
    }
}
