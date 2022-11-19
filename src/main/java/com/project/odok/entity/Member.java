package com.project.odok.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.odok.dto.requestDto.member.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member extends TimeStamped {
    @Id
    private String memberId;
    private String email;
    private String username;
    private String address;

    @Column(unique = true)
    private Long kakaoId;
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public Member(SignupRequestDto signupRequestDto, String password) {
        this.memberId = signupRequestDto.getMemberId();
        this.email = signupRequestDto.getEmail();
        this.username = signupRequestDto.getUsername();
        this.address = signupRequestDto.getAddress();
        this.phoneNumber = signupRequestDto.getPhoneNumber();
        this.password = password;
        this.authority = Authority.ROLE_USER;
    }

    public Member(String username, String encodedPassword, String email, Long kakaoId) {
        this.memberId = email;
        this.email = email;
        this.username = username;
        this.password = encodedPassword;
        this.kakaoId = kakaoId;
        this.authority = Authority.ROLE_USER;
    }

    public Member updateKakao(Member member, Long kakaoId) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.address = member.getAddress();
        this.phoneNumber = member.getPhoneNumber();
        this.password = member.getPassword();
        this.authority = Authority.ROLE_USER;
        this.kakaoId = kakaoId;
        return member;
    }

}
