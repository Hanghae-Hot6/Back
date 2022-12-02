package com.project.odok.dto.responseDto;


import com.project.odok.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {

    private String memberId;
    private String commnet;
    private LocalDateTime createdAt;

    public ReviewResponseDto (Review review){
        this.memberId = review.getMember().getMemberId();
        this.commnet = review.getComment();
        this.createdAt = review.getCreatedAt();
    }

}
