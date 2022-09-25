package com.amazingbooks.application.com.amazingbooks.repository;

import com.amazingbooks.application.com.amazingbooks.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> { }
