package com.project.odok.dto.requestDto.club;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class ClubRequestDto {

    @NotBlank(message = "모임명을 입력해주세요.")
    private String clubName;
    @NotBlank(message = "모임의 카테고리를 선택하세요.")
    private String category;
    @NotBlank(message = "모임을 간단히 소개해주세요.")
    private String clubIntro;
    @NotBlank(message = "모임에서 읽을 책을 선택하세요.")
    private String book1;
    private String book2;
    private String book3;

    private MultipartFile thumbnail;
    private String beforeThumbnail;

    @NotBlank(message = "모임 최대인원을 입력해주세요.")
    private String memberMaxNum;
    @NotBlank(message = "모임 시작일을 선택하세요.")
    private String startDate;
    @NotBlank(message = "모임 종료일을 선택하세요.")
    private String finishDate;
    @NotBlank(message = "모임 장소를 정해주세요.")
    private String location;
    @NotBlank(message = "모임의 일정을 입력해주세요.")
    private String schedule;
    @NotBlank(message = "모임에 대한 소개를 해주세요.")
    private String clubSummary;
    @NotBlank(message = "간단한 책 소개를 입력해주세요.")
    private String bookSummary;
}