package com.project.odok.dto.responseDto;

import com.project.odok.entity.Club;
import lombok.Getter;

@Getter
public class MyPageClubResponseDto {
    private Long clubId;
    private String clubName;
    private String startDate;
    private String finishDate;

    public MyPageClubResponseDto(Club club) {
        this.clubId = club.getClubId();
        this.clubName = club.getClubName();
        this.startDate = club.getStartDate();
        this.finishDate = club.getFinishDate();
    }
}
