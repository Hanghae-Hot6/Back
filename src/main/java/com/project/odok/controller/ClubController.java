package com.project.odok.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.ClubRequestDto;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;


    // 모임 등록
    @PostMapping
    public ResponseDto<?> createClub(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @RequestPart(value = "imageUrl", required = false)MultipartFile imageUrl,
                                     @RequestParam("data") String dataList) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ClubRequestDto clubRequestDto = objectMapper.readValue(dataList, new TypeReference<>(){});

        return clubService.createClub(clubRequestDto, imageUrl, userDetails.getMember());
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
                                     @RequestPart(value = "imageUrl", required = false)MultipartFile imageUrl,
                                     @RequestParam("data") String dataList) throws IOException{

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ClubRequestDto clubRequestDto = objectMapper.readValue(dataList, new TypeReference<>(){});

        return clubService.updateClub(clubId, userDetails.getMember(), clubRequestDto, imageUrl);
    }


    // 모임 삭제
    @DeleteMapping(value = "/{club-id}")
    public ResponseDto<?> deleteClub(@PathVariable(name = "club-id") Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return clubService.deleteClub(clubId, userDetails.getMember());
    }
}
