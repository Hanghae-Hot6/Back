package com.project.odok.dto.responseDto;

import com.project.odok.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubsInfoResponseDto {
    private Long clubId;
    private String thumbnail;
    private String clubName;
    private String memberId;
    private String category;
    private String summary;
    private String memberLimit;


    public ClubsInfoResponseDto(Club club){
        this.clubId = club.getClubId();
        this.thumbnail = club.getThumbnail();
        this.clubName = club.getClubName();
        this.memberId = club.getMember().getMemberId();
        this.category = club.getCategory();
        this.summary = club.getClubSummary();
        this.memberLimit = club.getMemberMaxNum();
    }
}
