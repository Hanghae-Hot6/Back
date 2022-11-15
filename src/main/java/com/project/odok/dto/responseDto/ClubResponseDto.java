package com.project.odok.dto.responseDto;

import com.project.odok.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubResponseDto {
    private Long clubId;
    private String leader;
    private String clubName;
    private String clubIntro;
    private String plan;
    private String location;
    private String schedule;
    private String memberLimit;
    private String category;
    private String summary;
    private String imageUrl;

    public ClubResponseDto(Club club){
        this.clubId = club.getClubId();
        this.leader = club.getMember().getMemberId();
        this.clubName = club.getClubName();
        this.clubIntro = club.getClubIntro();
        this.plan = club.getPlan();
        this.location = club.getLocation();
        this.schedule = club.getSchedule();
        this.memberLimit = club.getMemberLimit();
        this.category = club.getCategory();
        this.summary = club.getSummary();
        this.imageUrl = club.getImageUrl();
    }

}
