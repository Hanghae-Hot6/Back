package com.project.odok.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.TokenDto;
import com.project.odok.dto.requestDto.member.KakaoUserInfoDto;
import com.project.odok.dto.requestDto.member.LoginRequestDto;
import com.project.odok.dto.requestDto.member.SignupRequestDto;
import com.project.odok.entity.Member;
import com.project.odok.entity.RefreshToken;
import com.project.odok.repository.MemberRepository;
import com.project.odok.repository.RefreshTokenRepository;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.security.jwt.JwtFilter;
import com.project.odok.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

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

        createToken(response, authentication);
        return ResponseDto.success("로그인 성공");
    }

    public ResponseDto<?> kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        // 3. 필요시에 회원가입

        Member kakaoUsers = registerKakaoUserIfNeeded(kakaoUserInfo);
        UserDetailsImpl memberDetails = new UserDetailsImpl(kakaoUsers);
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        createToken(response, authentication);
        // 4. 강제 로그인 처리
        return ResponseDto.success(kakaoUsers.getMemberId());
    }

    private void createToken(HttpServletResponse response, Authentication authentication) {
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        response.setHeader("Refresh-Token", tokenDto.getRefreshToken());
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        // 카카오 RestAPI
        body.add("client_id", "876482e3e44b9d3cf41e27b33e243267");
//        body.add("redirect_uri", "http://localhost:3000/login");
        body.add("redirect_uri", "http://hot6-front.s3-website.ap-northeast-2.amazonaws.com/login");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // 여기를 통해 카카오 JSON 데이터를 꺼내옴 = 네이버 구글도 여기만 바꾸면 정보 가져올 수 있음
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email);
    }

    private Member registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        Member kakaoMember = memberRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoMember == null) {
            // 카카오 사용자 이메일과 동일한 이메일을 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            Member sameEmailMember = memberRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailMember != null) {
                kakaoMember = sameEmailMember;
                // 기존 회원정보에 카카오 Id 추가
                kakaoMember.updateKakao(kakaoMember, kakaoId);
            } else {
                // 신규 회원가입
                // username: kakao nickname
                String username = kakaoUserInfo.getUsername();

                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getEmail();
                kakaoMember = new Member(username, encodedPassword, email, kakaoId);
            }

            memberRepository.save(kakaoMember);
        }
        return kakaoMember;
    }
}
