package com.project.odok.dto.responseDto;

import com.project.odok.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubsInfoResponseDto {
    private String thumbnail;
    private String clubName;
    private String memberId;
    private String category;
    private String summary;
    private Long memberLimit;


    public ClubsInfoResponseDto(Club club){
        this.thumbnail = club.getImageUrl();
        this.clubName = club.getClubName();
        this.memberId = club.getMember().getMemberId();
        this.category = club.getCategory();
        this.summary = club.getSummary();
        this.memberLimit = club.getMemberLimit();
    }
}