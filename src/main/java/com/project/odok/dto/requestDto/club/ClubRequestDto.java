package com.project.odok.dto.requestDto.club;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubRequestDto {

    private String clubName;

    private String category;

    private MultipartFile thumbnail;

    private String memberMinNum;
    private String memberMaxNum;

    private String startDate;
    private String finishDate;

    private String location;

    private String schedule;

    private String clubIntro;

    private String clubSummary;

    private String book1;
    private String book2;
    private String book3;

    private String bookIntro;

    private String bookSummary;
}