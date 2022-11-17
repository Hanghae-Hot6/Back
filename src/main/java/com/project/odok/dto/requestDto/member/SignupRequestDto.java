package com.project.odok.dto.requestDto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    private String memberId;
    private String password;
    private String username;
    private String address;
    private String phoneNumber;
    private String email;
}

