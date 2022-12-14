package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.TokenDto;
import com.project.odok.dto.TokenRequestDto;
import com.project.odok.dto.requestDto.member.LoginRequestDto;
import com.project.odok.dto.requestDto.member.MemberModifyRequestDto;
import com.project.odok.dto.requestDto.member.SignupRequestDto;
import com.project.odok.entity.Member;
import com.project.odok.repository.ClubMemberRepository;
import com.project.odok.repository.ClubRepository;
import com.project.odok.repository.InterestRepository;
import com.project.odok.repository.MemberRepository;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.security.exception.ErrorCode;
import com.project.odok.security.exception.OdokExceptions;
import com.project.odok.security.jwt.JwtFilter;
import com.project.odok.security.jwt.TokenProvider;
import com.project.odok.service.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    public ResponseDto<?> signUp(SignupRequestDto signupRequestDto) {
        if (memberRepository.existsByMemberId(signupRequestDto.getMemberId()))
            return ResponseDto.fail("이미 가입된 유저입니다.");
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
        Member member = memberRepository.findById(loginRequestDto.getMemberId())
                .orElseThrow(() -> new OdokExceptions(ErrorCode.NOT_FOUND_MEMBER));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword()))
            throw new OdokExceptions(ErrorCode.NOT_MATCHED_PASSWORD);

        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        redisUtil.setDataExpire(authentication.getName(), tokenDto.getRefreshToken(), REFRESH_TOKEN_EXPIRE_TIME);
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        response.setHeader("Refresh-Token", tokenDto.getRefreshToken());
        return ResponseDto.success("로그인 성공");
    }

    public ResponseDto<?> reissue(TokenRequestDto tokenRequestDto, HttpServletResponse response) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            return ResponseDto.fail("Refresh Token이 유효하지 않습니다. 다시 로그인을 해주세요.");
        }
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken().substring(7));
        if (!redisUtil.getData(authentication.getName()).equals(tokenRequestDto.getRefreshToken())) {
            return ResponseDto.fail("토큰의 유저 정보가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        response.setHeader("Refresh-Token", tokenRequestDto.getRefreshToken());
        return ResponseDto.success("Access Token을 재발급 하였습니다.");
    }

    public ResponseDto<?> modifyMember(MemberModifyRequestDto memberModifyRequestDto, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        member.updateMember(member, memberModifyRequestDto, passwordEncoder.encode(memberModifyRequestDto.getPassword()));
        memberRepository.save(member);
        return ResponseDto.success("회원정보를 수정하였습니다.");
    }

    public ResponseDto<?> auth(String password, UserDetailsImpl userDetails) {
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            return ResponseDto.fail("패스워드가 일치하지 않습니다.");
        }
        return ResponseDto.success("비밀번호 인증에 성공하셨습니다.");
    }
}

