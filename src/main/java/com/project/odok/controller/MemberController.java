package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.LoginRequestDto;
import com.project.odok.dto.requestDto.SignupRequestDto;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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


}
