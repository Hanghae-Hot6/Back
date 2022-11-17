package com.project.odok.dto.requestDto.book;

import com.project.odok.entity.Book;
import lombok.Getter;

@Getter
public class BestSellerRequestDto {
    private String isbn;
    private String bookName;
    private String link;
    private String bookImage;
    private String author;
    private String description;
    private String publisher;
}
