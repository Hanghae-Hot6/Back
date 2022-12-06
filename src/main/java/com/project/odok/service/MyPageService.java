package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.responseDto.MyPageClubResponseDto;
import com.project.odok.dto.responseDto.MyPageResponseDto;
import com.project.odok.entity.Club;
import com.project.odok.entity.ClubMember;
import com.project.odok.entity.Interest;
import com.project.odok.repository.ClubMemberRepository;
import com.project.odok.repository.ClubRepository;
import com.project.odok.repository.InterestRepository;
import com.project.odok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final InterestRepository interestRepository;

    public ResponseDto<?> myPage(UserDetailsImpl userDetails) {

        List<ClubMember> joinClub = clubMemberRepository.findAllByMember(userDetails.getMember());
        List<MyPageClubResponseDto> clubList = new ArrayList<>();

        for (ClubMember club:joinClub) {
            clubList.add(new MyPageClubResponseDto(club.getClub()));
        }
        return ResponseDto.success(new MyPageResponseDto(userDetails.getMember(), clubList));
    }

    public ResponseDto<?> myPageMadeByMe(UserDetailsImpl userDetails) {

        List<Club> madeClub = clubRepository.findAllByLeader(userDetails.getMember());
        List<MyPageClubResponseDto> clubList = new ArrayList<>();
        for (Club club : madeClub) {
            clubList.add(new MyPageClubResponseDto(club));
        }
        return ResponseDto.success(clubList);
    }

    public ResponseDto<?> myPageInterest(UserDetailsImpl userDetails) {

        List<Interest> interestClub = interestRepository.findAllByMember(userDetails.getMember());
        List<MyPageClubResponseDto> clubList = new ArrayList<>();
        for (Interest interest : interestClub) {
            clubList.add(new MyPageClubResponseDto(interest.getClub()));
        }
        return ResponseDto.success(clubList);
    }

}
