package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.ClubRequestDto;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;

    // 모임 등록
    @PostMapping
    public ResponseDto<?> createClub(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute ClubRequestDto clubRequestDto) throws IOException {
        return clubService.createClub(clubRequestDto, userDetails.getMember());
    }

    // 모임 전체 조회
    @GetMapping
    public ResponseDto<?> getClubList(){
        return clubService.getClubList();
    }

    // 모임 상세 조회
    @GetMapping(value = "/{club-id}")
    public ResponseDto<?> getClub(@PathVariable(name = "club-id") Long clubId){
        return clubService.getClub(clubId);
    }

    // 모임 수정
    @PutMapping(value = "/{club-id}")
    public ResponseDto<?> updateClub(@PathVariable(name = "club-id") Long clubId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @ModelAttribute ClubRequestDto clubRequestDto) throws IOException{
        return clubService.updateClub(clubId, userDetails.getMember(), clubRequestDto);
    }

    // 모임 삭제
    @DeleteMapping(value = "/{club-id}")
    public ResponseDto<?> deleteClub(@PathVariable(name = "club-id") Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return clubService.deleteClub(clubId, userDetails.getMember());
    }
}
