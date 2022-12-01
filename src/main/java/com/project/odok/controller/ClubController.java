package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.club.ClubRequestDto;
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

    @PostMapping
    public ResponseDto<?> createClub(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @ModelAttribute ClubRequestDto clubRequestDto) throws IOException{

        return clubService.createClub(userDetails.getMember(), clubRequestDto);
    }


    @GetMapping
    public ResponseDto<?> getClubList(){
        return clubService.getClubList();
    }


    @GetMapping(value = "/top5")
    public ResponseDto<?> getTop5Clubs(){
        return clubService.getTop5Clubs();
    }


    @GetMapping(value = "/{club-id}")
    public ResponseDto<?> getClub(@PathVariable(name = "club-id") Long clubId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails){

        return clubService.getClub(clubId, userDetails.getMember());
    }


    @PutMapping(value = "/{club-id}")
    public ResponseDto<?> updateClub(@PathVariable(name = "club-id") Long clubId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @ModelAttribute ClubRequestDto clubRequestDto) throws IOException{

        return clubService.updateClub(clubId, userDetails.getMember(), clubRequestDto);
    }


    @DeleteMapping(value = "/{club-id}")
    public ResponseDto<?> deleteClub(@PathVariable(name = "club-id") Long clubId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){

        return clubService.deleteClub(clubId, userDetails.getMember());
    }


    @PostMapping(value = "/{club-id}/join")
    public ResponseDto<?> joinClub(@PathVariable(name = "club-id") Long clubId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){

        return clubService.joinClub(clubId, userDetails.getMember());
    }


    @DeleteMapping(value = "/{club-id}/withdraw")
    public ResponseDto<?> withdrawClub(@PathVariable(name = "club-id") Long clubId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){

        return clubService.withdrawClub(clubId, userDetails.getMember());
    }

}