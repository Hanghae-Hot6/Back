package com.project.odok.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Book extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String bookImage;

    @Column(nullable = false)
    private String writer;

//    @Column(columnDefinition = "LONGTEXT")
//    private String summary;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String isbn;



    public Book (String bookName, String link, String image, String author, String publisher, String isbn) {
        this.bookName = bookName;
        this.link = link;
        this.bookImage = image;
        this.writer = author;
//        this.summary = description;
        this.publisher = publisher;
        this.isbn = isbn;
    }
}
