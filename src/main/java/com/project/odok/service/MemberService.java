package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.TokenDto;
import com.project.odok.dto.requestDto.member.LoginRequestDto;
import com.project.odok.dto.requestDto.member.SignupRequestDto;
import com.project.odok.dto.responseDto.MyPageClubResponseDto;
import com.project.odok.dto.responseDto.MyPageResponseDto;
import com.project.odok.entity.Club;
import com.project.odok.entity.ClubMember;
import com.project.odok.entity.Member;
import com.project.odok.repository.ClubMemberRepository;
import com.project.odok.repository.ClubRepository;
import com.project.odok.repository.MemberRepository;
import com.project.odok.security.UserDetailsImpl;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
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
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        Member member = memberRepository.findById(loginRequestDto.getMemberId())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword()))
            throw new RuntimeException("패스워드가 일치하지 않습니다.");

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        redisUtil.setDataExpire(authentication.getName(), tokenDto.getRefreshToken(), REFRESH_TOKEN_EXPIRE_TIME);
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        response.setHeader("Refresh-Token", tokenDto.getRefreshToken());
        return ResponseDto.success("로그인 성공");
    }

    public ResponseDto<?> myPage(UserDetailsImpl userDetails) {
        List<ClubMember> joinClub = clubMemberRepository.findAllByMember(userDetails.getMember());
        List<MyPageClubResponseDto> clubList = new ArrayList<>();

        for (ClubMember club:joinClub) {
            clubList.add(new MyPageClubResponseDto(club.getClub()));
        }
        return ResponseDto.success(new MyPageResponseDto(userDetails.getMember(), clubList));
    }

    public ResponseDto<?> myPageMadeByMe(UserDetailsImpl userDetails) {
        List<Club> madeClub = clubRepository.findAllByLeader(userDetails.getMember());
        List<MyPageClubResponseDto> clubList = new ArrayList<>();
        for (Club club : madeClub) {
            clubList.add(new MyPageClubResponseDto(club));
        }
        return ResponseDto.success(clubList);
    }
}

