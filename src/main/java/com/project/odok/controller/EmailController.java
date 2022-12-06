package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.member.FindIdRequestDto;
import com.project.odok.dto.requestDto.member.FindPasswordRequestDto;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/confirm")
    public ResponseDto<?> mailConfirm(@RequestParam String email) throws Exception {
        return emailService.sendSimpleMessage(email);
    }

    @GetMapping("/auth")
    public ResponseDto<?> mailAuth(@RequestParam String code) {
        return emailService.verifyEmail(code);
    }

    @PostMapping("/findId")
    public ResponseDto<?> findId(@RequestBody FindIdRequestDto requestDto) throws Exception {
        return emailService.sendIdMessage(requestDto);
    }

    @PostMapping("/findPassword")
    public ResponseDto<?> findPassword(@RequestBody FindPasswordRequestDto findPasswordRequestDto) throws Exception {
        return emailService.sendPasswordMessage(findPasswordRequestDto);
    }

    @PostMapping("/cs")
    public ResponseDto<?> customerService(@RequestBody String content, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        return emailService.customerService(userDetails.getMember(), content);
    }

}
