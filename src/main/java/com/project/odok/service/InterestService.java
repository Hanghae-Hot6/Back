package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.entity.Club;
import com.project.odok.entity.Interest;
import com.project.odok.entity.Member;
import com.project.odok.repository.ClubRepository;
import com.project.odok.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final ClubRepository clubRepository;
    private final InterestRepository interestRepository;


    // 모임 관심 체크/취소
    public ResponseDto<?> interestClub(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NullPointerException("해당 모임이 존재하지 않습니다."));

        if (!interestRepository.existsByMemberAndClub(member, club)){

            Interest interest = new Interest(member,club);
            interestRepository.save(interest);

            return ResponseDto.success("관심 모임 완료");
        }

        Interest interest = interestRepository.findByMemberAndClub(member,club);
        interestRepository.delete(interest);

        return ResponseDto.success("관심 모임 취소");
    }
}
