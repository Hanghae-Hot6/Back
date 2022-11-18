package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.TokenDto;
import com.project.odok.security.exception.requestDto.LoginRequestDto;
import com.project.odok.security.exception.requestDto.SignupRequestDto;
import com.project.odok.entity.Member;
import com.project.odok.entity.RefreshToken;
import com.project.odok.repository.MemberRepository;
import com.project.odok.repository.RefreshTokenRepository;
import com.project.odok.security.jwt.JwtFilter;
import com.project.odok.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public ResponseDto<?> signUp(SignupRequestDto signupRequestDto) {
        Member member = new Member(signupRequestDto, passwordEncoder.encode(signupRequestDto.getPassword()));
        memberRepository.save(member);
        return ResponseDto.success("회원가입 되었습니다.");
    }

    public ResponseDto<?> checkId(String id) {
        if (memberRepository.existsByMemberId(id))
            return ResponseDto.fail("사용 중인 ID입니다.");

        return ResponseDto.success("사용 가능한 ID 입니다.");
    }

    public ResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        Member member = memberRepository.findById(loginRequestDto.getMemberId())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword()))
            throw new RuntimeException("패스워드가 일치하지 않습니다.");

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        response.setHeader("Refresh-Token", tokenDto.getRefreshToken());

        return ResponseDto.success("로그인 성공");
    }
}
