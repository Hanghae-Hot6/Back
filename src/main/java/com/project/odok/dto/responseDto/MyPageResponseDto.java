package com.project.odok.dto.responseDto;

import com.project.odok.entity.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class MyPageResponseDto {
    private String memberId;
    private String email;
    private String username;
    private String address;
    private String phoneNumber;
    private List<MyPageClubResponseDto> clubList;

    public MyPageResponseDto(Member member, List<MyPageClubResponseDto> clubList) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.address = member.getAddress();
        this.phoneNumber = member.getPhoneNumber();
        this.clubList = clubList;
    }
}
