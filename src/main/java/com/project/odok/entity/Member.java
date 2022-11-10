package com.project.odok.entity;


import com.project.odok.security.exception.requestDto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Member extends TimeStamped {
    @Id
    private String memberId;
    private String email;
    private String username;
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    public Member(SignupRequestDto signupRequestDto, String password) {
        this.memberId = signupRequestDto.getMemberId();
        this.email = signupRequestDto.getEmail();
        this.username = signupRequestDto.getUsername();
        this.address = signupRequestDto.getAddress();
        this.phoneNumber = signupRequestDto.getPhoneNumber();
        this.password = password;
    }

}
