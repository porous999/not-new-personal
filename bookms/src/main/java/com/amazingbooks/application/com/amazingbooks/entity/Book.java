package com.amazingbooks.application.com.amazingbooks.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name = "book_table")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int bookId;
    String author;
    String isbn;
    String title;
    Date publishedDate;
    int totalCopies;
    int issuedCopies;
}
