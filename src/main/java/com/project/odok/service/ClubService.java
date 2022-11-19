package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.club.ClubRequestDto;
import com.project.odok.dto.responseDto.ClubsInfoResponseDto;
import com.project.odok.dto.responseDto.ClubResponseDto;
import com.project.odok.entity.*;
import com.project.odok.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private final BookRepository bookRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubBookReqository clubBookReqository;
    private final S3UploadService s3UploadService;
    private final InterestRepository interestRepository;


    // 모임 등록
    public ResponseDto<?> createClub(Member member, ClubRequestDto clubRequestDto) throws IOException {

        Club club = new Club(clubRequestDto, member, s3UploadService, dir);
        clubRepository.save(club);

        Book book1 = bookRepository.findByIsbn(clubRequestDto.getBook1());
        Book book2 = bookRepository.findByIsbn(clubRequestDto.getBook2());
        Book book3 = bookRepository.findByIsbn(clubRequestDto.getBook3());

        ClubBook clubBook = new ClubBook(club, book1, book2, book3, clubRequestDto);
        clubBookReqository.save(clubBook);

        ClubMember clubMember = new ClubMember(club, member);
        clubMemberRepository.save(clubMember);

        return ResponseDto.success("모임 등록 완료");
    }


    // 모임 전체 조회
    public ResponseDto<?> getClubList() {

        List<Club> clubList = clubRepository.findAllByOrderByCreatedAtDesc();
        List<ClubsInfoResponseDto> clubResponseDtoList = new ArrayList<>();

        for (Club club : clubList) {

            clubResponseDtoList.add(new ClubsInfoResponseDto(club));
        }
        return ResponseDto.success(clubResponseDtoList);
    }


    // 모임 상세 조회
    @Transactional
    public ResponseDto<?> getClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NullPointerException("해당 모임이 존재하지 않습니다."));
        club.updateVisitCount();

        ClubBook clubBook = clubBookReqository.findByClub(club);

        if (!clubMemberRepository.existsByMemberAndClub(member, club) && !interestRepository.existsByMemberAndClub(member, club)){
            return ResponseDto.success(new ClubResponseDto(club,clubBook,false,false));
        }

        else if (!clubMemberRepository.existsByMemberAndClub(member, club) && interestRepository.existsByMemberAndClub(member, club)) {
            return ResponseDto.success(new ClubResponseDto(club,clubBook,false,true));
        }

        else if (clubMemberRepository.existsByMemberAndClub(member, club) && !interestRepository.existsByMemberAndClub(member, club)) {
            return ResponseDto.success(new ClubResponseDto(club,clubBook,true,false));
        }


        return ResponseDto.success(new ClubResponseDto(club, clubBook,true,true));
    }


    // 모임 수정
    @Transactional
    public ResponseDto<?> updateClub(Long clubId, Member member, ClubRequestDto clubRequestDto) throws IOException {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NullPointerException("해당 모임이 존재하지 않습니다."));

        if (validateMember(member, club))
            throw new IllegalArgumentException("모임의 생성자와 현재 사용자가 일치하지 않습니다.");

        club.update(clubRequestDto, s3UploadService, dir);

        return ResponseDto.success("모임정보 수정 완료");
    }


    // 모임 삭제
    @Transactional
    public ResponseDto<?> deleteClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NullPointerException("해당 모임이 존재하지 않습니다."));

        if (validateMember(member, club))
            throw new IllegalArgumentException("모임의 생성자와 현재 사용자가 일치하지 않습니다.");

        clubRepository.delete(club);

        return ResponseDto.success("모임 삭제 완료");
    }


    // 모임 가입하기
    public ResponseDto<?> joinClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NullPointerException("해당 모임이 존재하지 않습니다."));

        ClubMember clubMember = new ClubMember(club, member);

        if (!clubMemberRepository.existsByMemberAndClub(member, club)){
            clubMemberRepository.save(clubMember);
            return ResponseDto.success("모임 가입 완료");
        }

        return ResponseDto.success("이미 가입된 유저입니다.");
    }


    // 모임 탈퇴하기
    @Transactional
    public ResponseDto<?> withdrawClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NullPointerException("해당 모임이 존재하지 않습니다."));

        if (!clubMemberRepository.existsByMemberAndClub(member,club))
            return ResponseDto.success("모임에 가입하지 않은 유저입니다.");

        ClubMember clubMember = clubMemberRepository.findByMemberAndClub(member,club);

        clubMemberRepository.delete(clubMember);

        return ResponseDto.success("모임가입 취소 완료");
    }

    public boolean validateMember(Member member, Club club) {
        return !member.getMemberId().equals(club.getMember().getMemberId());
    }


}