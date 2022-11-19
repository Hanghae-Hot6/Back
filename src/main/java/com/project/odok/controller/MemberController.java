package com.project.odok.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.member.FindIdRequestDto;
import com.project.odok.dto.requestDto.member.LoginRequestDto;
import com.project.odok.dto.requestDto.member.SignupRequestDto;
import com.project.odok.service.EmailService;
import com.project.odok.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

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
        return memberService.kakaoLogin(code, response);
    }

    @GetMapping("/mailConfirm")
    public ResponseDto<?> mailConfirm(@RequestParam String email) throws Exception {
        return emailService.sendSimpleMessage(email);
    }

    @GetMapping("/mailAuth")
    public ResponseDto<?> mailAuth(@RequestParam String code) {
        return emailService.verifyEmail(code);
    }

    @PostMapping("findId")
    public ResponseDto<?> findId(@RequestBody FindIdRequestDto requestDto) throws Exception {
        return emailService.sendEmailMessage(requestDto);
    }
}
