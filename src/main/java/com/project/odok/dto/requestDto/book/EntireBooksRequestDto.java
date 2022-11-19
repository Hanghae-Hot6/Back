package com.project.odok.dto.requestDto.book;

import lombok.Getter;

@Getter
public class EntireBooksRequestDto {
    private String isbn;
    private String bookName;
    private String link;
    private String bookImage;
    private String author;
    private String description;
    private String publisher;
}
