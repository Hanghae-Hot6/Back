package com.project.odok.dto.requestDto.member;

import com.project.odok.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String memberId;
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberId, password);
    }

    public LoginRequestDto(Member member) {
        this.memberId = member.getMemberId();
        this.password = member.getPassword();
    }
}
