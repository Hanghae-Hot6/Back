package com.project.odok.controller;

import com.project.odok.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/search/{keyword}")
    public String searchBook(@PathVariable String keyword){
        return bookService.searchResult(keyword);
    }
}
