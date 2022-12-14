package com.project.odok.dto.responseDto;


import com.project.odok.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {

    private Long reviewId;
    private String memberId;
    private String comment;
    private LocalDateTime createdAt;

    public ReviewResponseDto (Review review){
        this.reviewId = review.getReviewId();
        this.memberId = review.getMember().getMemberId();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
    }

}
