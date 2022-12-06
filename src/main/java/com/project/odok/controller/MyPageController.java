package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.member.MemberModifyRequestDto;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.MemberService;
import com.project.odok.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    private final MyPageService myPageService;
    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "My Page", description = "마이페이지")
    public ResponseDto<?> myPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.myPage(userDetails);
    }

    @GetMapping("/leader")
    @Operation(summary = "내가 만든 모임", description = "내가 만든 모임 리스트")
    public ResponseDto<?> myPageMadeByMe(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.myPageMadeByMe(userDetails);
    }

    @GetMapping("/interest")
    @Operation(summary = "관심 있는 모임", description = "내가 관심 있는 모임 리스트")
    public ResponseDto<?> myPageInterest(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.myPageInterest(userDetails);
    }

    @PostMapping("/modify")
    public ResponseDto<?> modifyMember(@RequestBody MemberModifyRequestDto memberModifyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.modifyMember(memberModifyRequestDto, userDetails);
    }
}