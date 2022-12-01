package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.club.ClubRequestDto;
import com.project.odok.dto.responseDto.ClubsInfoResponseDto;
import com.project.odok.dto.responseDto.ClubResponseDto;
import com.project.odok.entity.*;
import com.project.odok.repository.*;
import com.project.odok.security.exception.customexceptions.InvalidWriterException;
import com.project.odok.security.exception.customexceptions.NotFoundClubException;
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
    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;


    @Transactional
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

        chatRoomService.createdMemberChatRoom(member, club);

        return ResponseDto.success("모임 등록 완료");
    }


    public ResponseDto<?> getClubList() {

        List<Club> clubList = clubRepository.findAllByOrderByCreatedAtDesc();
        List<ClubsInfoResponseDto> clubResponseDtoList = new ArrayList<>();

        for (Club club : clubList) {

            clubResponseDtoList.add(new ClubsInfoResponseDto(club));
        }
        return ResponseDto.success(clubResponseDtoList);
    }


    public ResponseDto<?> getTop5Clubs() {

        List<Club> clubList = clubRepository.findTop5ByOrderByVisitNumDesc();
        List<ClubsInfoResponseDto> clubResponseDtoList = new ArrayList<>();

        for (Club club : clubList) {

            clubResponseDtoList.add(new ClubsInfoResponseDto(club));
        }
        return ResponseDto.success(clubResponseDtoList);
    }


    @Transactional
    public ResponseDto<?> getClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundClubException::new);
        club.updateVisitCount();

        ClubBook clubBook = clubBookReqository.findByClub(club);

        Integer clubMemberNum = clubMemberRepository.countAllByClub(club);

        if (!clubMemberRepository.existsByMemberAndClub(member, club) && !interestRepository.existsByMemberAndClub(member, club)) {
            return ResponseDto.success(new ClubResponseDto(club, clubBook, false, false, String.valueOf(clubMemberNum)));
        } else if (!clubMemberRepository.existsByMemberAndClub(member, club) && interestRepository.existsByMemberAndClub(member, club)) {
            return ResponseDto.success(new ClubResponseDto(club, clubBook, false, true, String.valueOf(clubMemberNum)));
        } else if (clubMemberRepository.existsByMemberAndClub(member, club) && !interestRepository.existsByMemberAndClub(member, club)) {
            return ResponseDto.success(new ClubResponseDto(club, clubBook, true, false, String.valueOf(clubMemberNum)));
        } else {
            return ResponseDto.success(new ClubResponseDto(club, clubBook, true, true, String.valueOf(clubMemberNum)));
        }
    }


    @Transactional
    public ResponseDto<?> updateClub(Long clubId, Member member, ClubRequestDto clubRequestDto) throws IOException {

        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundClubException::new);

        if (validateMember(member, club))
            throw new InvalidWriterException();

        Book book1 = bookRepository.findByIsbn(clubRequestDto.getBook1());
        Book book2 = bookRepository.findByIsbn(clubRequestDto.getBook2());
        Book book3 = bookRepository.findByIsbn(clubRequestDto.getBook3());

        ClubBook clubBook = clubBookReqository.findByClub(club);

        club.update(clubRequestDto, s3UploadService, dir);
        clubBook.update(book1, book2, book3, clubRequestDto);

        return ResponseDto.success("모임정보 수정 완료");
    }


    @Transactional
    public ResponseDto<?> deleteClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundClubException::new);

        if (validateMember(member, club))
            throw new InvalidWriterException();

        ChatRoom chatRoom = chatRoomRepository.findByTitle(club.getClubName());

        clubRepository.delete(club);

        chatRoomRepository.delete(chatRoom);

        return ResponseDto.success("모임 삭제 완료");
    }


    @Transactional
    public ResponseDto<?> joinClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundClubException::new);

        if (clubMemberRepository.countAllByClub(club) >= Integer.parseInt(club.getMemberMaxNum())) {
            return ResponseDto.fail("가입 정원이 초과되었습니다.");
        }

        ClubMember clubMember = new ClubMember(club, member);

        if (!clubMemberRepository.existsByMemberAndClub(member, club)) {
            clubMemberRepository.save(clubMember);

            chatRoomService.addMemberChatRoom(member, club);
            return ResponseDto.success("모임 가입 완료");
        }

        return ResponseDto.success("이미 가입된 유저입니다.");
    }


    @Transactional
    public ResponseDto<?> withdrawClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundClubException::new);

        if (!clubMemberRepository.existsByMemberAndClub(member, club))
            return ResponseDto.success("모임에 가입하지 않은 유저입니다.");

        ClubMember clubMember = clubMemberRepository.findByMemberAndClub(member, club);

        if (member.getMemberId().equals(club.getLeader().getMemberId())) {
            deleteClub(clubId, member);
        } else {
            clubMemberRepository.delete(clubMember);

            chatRoomService.deleteMemberChatRoom(member, club);
        }

        return ResponseDto.success("모임가입 취소 완료");
    }


    public boolean validateMember(Member member, Club club) {
        return !member.getMemberId().equals(club.getLeader().getMemberId());
    }

}