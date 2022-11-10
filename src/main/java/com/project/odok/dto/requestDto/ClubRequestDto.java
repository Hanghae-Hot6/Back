package com.project.odok.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ClubRequestDto {

    private String clubName;

    private String clubIntro;

    private String plan;

    private String book1;

    private String book2;

    private String book3;

    private String location;

    private String schedule;

    private Long memberLimit;

    private String category;

    private String summary;

    private MultipartFile imageUrl;
}
