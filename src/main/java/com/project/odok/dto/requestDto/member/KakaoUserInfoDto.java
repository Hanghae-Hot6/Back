package com.project.odok.dto.requestDto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String username;
    private String email;
}
