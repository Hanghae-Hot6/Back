package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.book.BookRequestDto;
import com.project.odok.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/search")
    public ResponseDto<?> searchBook(@RequestParam("keyword") String keyword,
                                     @RequestParam("start") String start,
                                     @RequestParam("display") String display){
        return bookService.searchResult(keyword, start, display);
    }

    @PostMapping()
    public ResponseDto<?> crawlBooks(@RequestBody List<BookRequestDto> bookRequestDto) {
        if (bookRequestDto.get(0).getIdentity().equals("All")) {
            return bookService.crawlBooks(bookRequestDto);
        }
        return bookService.crawlBestSeller(bookRequestDto);
    }
}
