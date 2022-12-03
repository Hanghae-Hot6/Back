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

    private String clubIntro;

    private String book1;
    private String book2;
    private String book3;

    private MultipartFile thumbnail;
    private String beforeThumbnail;

    private String memberMaxNum;

    private String startDate;
    private String finishDate;

    private String location;

    private String schedule;

    private String clubSummary;

    private String bookSummary;
}