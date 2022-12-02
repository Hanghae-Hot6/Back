package com.project.odok.dto.responseDto;

import com.project.odok.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClubReviewResponseDto {
    private Long clubId;
    private String thumbnail;
    private List<ReviewResponseDto> reviewList;

    public ClubReviewResponseDto(Club club, List<ReviewResponseDto> reviewList){
        this.clubId = club.getClubId();
        this.thumbnail = club.getThumbnail();
        this.reviewList = reviewList;
    }
}
