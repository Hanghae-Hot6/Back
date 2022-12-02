package com.project.odok.dto.requestDto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberModifyRequestDto {
    private String address;
    private String phoneNumber;
    private String password;
    private String passwordCheck;
}
