package com.project.odok.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.TokenRequestDto;
import com.project.odok.dto.requestDto.member.*;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.MemberService;
import com.project.odok.service.auth.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final KakaoService kakaoService;

    @PostMapping("/signup")
    @Operation(summary = "Sign Up", description = "회원가입")
    public ResponseDto<?> signUp(@RequestBody SignupRequestDto signupRequestDto) {
        return memberService.signUp(signupRequestDto);
    }

    @GetMapping("/signup/checkid/{id}")
    @Operation(summary = "ID Check", description = "멤버 ID 확인")
    public ResponseDto<?> checkId(@PathVariable String id) {
        return memberService.checkId(id);
    }

    @PostMapping("/login")
    @Operation(summary = "Log In", description = "로그인")
    public ResponseDto<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        return memberService.login(loginRequestDto, httpServletResponse);
    }

    @GetMapping("/kakao")
    @Operation(summary = "Kakao Login", description = "카카오 로그인")
    public ResponseDto<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code, response);
    }

    @PostMapping("/reissue")
    public ResponseDto<?> reissue(@RequestBody TokenRequestDto tokenRequestDto, HttpServletResponse response) {
        return memberService.reissue(tokenRequestDto, response);
    }

    @PostMapping("/auth")
    public ResponseDto<?> auth(@RequestBody String password, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.auth(password, userDetails);
    }
}

