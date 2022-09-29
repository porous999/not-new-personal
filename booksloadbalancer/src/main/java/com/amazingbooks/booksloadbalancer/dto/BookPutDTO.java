package com.amazingbooks.booksloadbalancer.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class BookPutDTO {

    String author;
    String isbn;
    String title;
    String publishedDateStr;
    Date publishedDate;
    int totalCopies;
    int issuedCopies;

    public void setPublishedDate() {
        publishedDate = Date.valueOf(publishedDateStr);
    }

    /*public String getPublishedDate() {
        return publishedDate.toLocalDate().format(DateTimeFormatter.ISO_DATE);
    }
*/
}
