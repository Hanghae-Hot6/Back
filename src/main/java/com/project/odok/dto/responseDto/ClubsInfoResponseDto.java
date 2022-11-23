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
    private String leader;
    private String category;
    private long visitNum;
    private String summary;
    private String memberLimit;
    private String location;
    private String startDate;
    private String finishDate;


    public ClubsInfoResponseDto(Club club){
        this.clubId = club.getClubId();
        this.thumbnail = club.getThumbnail();
        this.clubName = club.getClubName();
        this.leader = club.getLeader().getMemberId();
        this.category = club.getCategory();
        this.visitNum = club.getVisitNum();
        this.summary = club.getClubSummary();
        this.memberLimit = club.getMemberMaxNum();
        this.location = club.getLocation();
        this.startDate = club.getStartDate();
        this.finishDate = club.getFinishDate();
    }
}
