package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.ClubRequestDto;
import com.project.odok.dto.responseDto.ClubsInfoResponseDto;
import com.project.odok.dto.responseDto.ClubResponseDto;
import com.project.odok.entity.Club;
import com.project.odok.entity.ClubBook;
import com.project.odok.entity.ClubMember;
import com.project.odok.entity.Member;
import com.project.odok.repository.ClubBookReqository;
import com.project.odok.repository.ClubMemberRepository;
import com.project.odok.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    @Value("${cloud.aws.s3.dir}")
    private String dir;

    private final ClubRepository clubRepository;

    private final ClubMemberRepository clubMemberRepository;

    private final S3UploadService s3UploadService;


    // 모임 등록
    public ResponseDto<?> createClub(ClubRequestDto clubRequestDto, MultipartFile imageUrl, Member member) throws IOException {

        Club club = new Club(clubRequestDto, member, imageUrl, s3UploadService, dir);
        clubRepository.save(club);

        ClubMember clubMember = new ClubMember(club,member);
        clubMemberRepository.save(clubMember);

        return ResponseDto.success("모임 등록 완료");
    }


    // 모임 전체 조회
    public ResponseDto<?> getClubList() {

        List<Club> clubList = clubRepository.findAllByOrderByCreatedAtDesc();
        List<ClubsInfoResponseDto> clubResponseDtoList = new ArrayList<>();

        for(Club club : clubList){

            clubResponseDtoList.add(new ClubsInfoResponseDto(club));
        }
        return ResponseDto.success(clubResponseDtoList);
    }


    // 모임 상세 조회
    public ResponseDto<?> getClub(Long clubId) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NullPointerException("해당 모임이 존재하지 않습니다."));

        return ResponseDto.success(new ClubResponseDto(club) );
    }


    // 모임 수정
    @Transactional
    public ResponseDto<?> updateClub(Long clubId, Member member, ClubRequestDto clubRequestDto, MultipartFile imageUrl) throws IOException{

        Club club = clubRepository.findById(clubId).orElseThrow(()-> new NullPointerException("해당 모임이 존재하지 않습니다."));

        if (validateMember(member, club))
            throw new IllegalArgumentException("모임의 리더와 현재 사용자가 일치하지 않습니다.");

        club.update(clubRequestDto,imageUrl, s3UploadService, dir);

        return ResponseDto.success("모임정보 수정 완료");
    }


    // 모임 삭제
    @Transactional
    public ResponseDto<?> deleteClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(()-> new NullPointerException("해당 모임이 존재하지 않습니다."));

        if (validateMember(member, club))
            throw new IllegalArgumentException("모임의 리더와 현재 사용자가 일치하지 않습니다.");

        clubRepository.delete(club);

        return ResponseDto.success("모임 삭제 완료");
    }


    public boolean validateMember(Member member, Club club) {
        return !member.getMemberId().equals(club.getMember().getMemberId());
    }


}
