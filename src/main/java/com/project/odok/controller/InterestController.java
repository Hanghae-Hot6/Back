package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/clubs/{club-id}")
public class InterestController {
    private final InterestService interestService;

    // 모임 관심 체크/취소
    @PostMapping(value = "/interest")
    public ResponseDto<?> interestClub(@PathVariable(name = "club-id") Long clubId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){

        return interestService.interestClub(clubId, userDetails.getMember());
    }

}
