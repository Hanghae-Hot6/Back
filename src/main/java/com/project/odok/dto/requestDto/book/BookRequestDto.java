package com.project.odok.dto.requestDto.book;

import lombok.Getter;

@Getter
public class BookRequestDto {
    private String isbn;
    private String bookName;
    private String link;
    private String bookImage;
    private String author;
    private String description;
    private String publisher;
    private String identity;
}
