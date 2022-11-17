package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.book.BestSellerRequestDto;
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
    public ResponseDto<?> crawlBooks(@RequestBody List<BestSellerRequestDto> bestSellerRequestDto) {

        return bookService.crawlBestSeller(bestSellerRequestDto);
    }
}
