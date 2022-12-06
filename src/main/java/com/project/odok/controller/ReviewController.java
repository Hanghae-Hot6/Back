package com.project.odok.controller;


import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.review.ReviewRequestDto;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{clubid}/reviews/getall")
    public ResponseDto<?> getAllReview(@PathVariable(name = "clubid") Long clubId){
        return reviewService.getAllReview(clubId);
    }

    @GetMapping("/reviews")
    public ResponseDto<?> getReviewList(){
        return reviewService.getReviewList();
    }

    @PostMapping("/{clubid}/reviews/create")
    public ResponseDto<?> createdReview (@PathVariable(name = "clubid") Long clubId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody ReviewRequestDto reviewRequestDto){
        return reviewService.createReview(clubId, userDetails, reviewRequestDto);
    }

    @DeleteMapping("/{reviewid}/delete")
    public ResponseDto<?> deleteReview (@PathVariable(name = "reviewid") Long reviewId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reviewService.deleteReview(reviewId, userDetails);
    }

}