package com.project.odok.entity;

import com.project.odok.dto.requestDto.book.BookRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Book extends TimeStamped {
    @Id
    private String isbn;
    private String bookName;
    private String link;
    private String bookImage;
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String publisher;



    public Book (String bookName, String link, String image, String author, String description,String publisher, String isbn) {
        this.bookName = bookName;
        this.link = link;
        this.bookImage = image;
        this.author = author;
        this.description = description;
        this.publisher = publisher;
        this.isbn = isbn;
    }

    public Book(BookRequestDto book) {
        this.bookName = book.getBookName();
        this.link = book.getLink();
        this.bookImage = book.getBookImage();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.publisher = book.getPublisher();
        this.isbn = book.getIsbn();
    }
}
