package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.entity.Club;
import com.project.odok.entity.Interest;
import com.project.odok.entity.Member;
import com.project.odok.repository.ClubRepository;
import com.project.odok.repository.InterestRepository;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.security.exception.ErrorCode;
import com.project.odok.security.exception.OdokExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final ClubService clubService;
    private final ClubRepository clubRepository;
    private final InterestRepository interestRepository;


    // 모임 관심 체크/취소
    public ResponseDto<?> interestClub(Long clubId, UserDetailsImpl userDetails) {

        Member member = userDetails.getMember();

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new OdokExceptions(ErrorCode.NOT_FOUND_CLUB));

        if (!clubService.interestCheck(member, club)) {

            Interest interest = new Interest(member, club);
            interestRepository.save(interest);

            return ResponseDto.success("관심 모임 등록");
        }

        Interest interest = interestRepository.findByMemberAndClub(member, club);
        interestRepository.delete(interest);

        return ResponseDto.success("관심 등록 취소");
    }
}