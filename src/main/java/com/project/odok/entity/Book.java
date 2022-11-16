package com.project.odok.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Book extends TimeStamped {
    @Id
    private String isbn;
    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String bookImage;

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
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
}
