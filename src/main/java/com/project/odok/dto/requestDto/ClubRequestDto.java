package com.project.odok.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubRequestDto {

    private String clubName;

    private String clubIntro;

    private String plan;

    private String location;

    private String schedule;

    private String memberLimit;

    private String category;

    private String summary;

}
