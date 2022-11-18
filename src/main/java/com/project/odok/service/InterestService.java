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
    private final InterestRepository interestRepository;
    private final ClubRepository clubRepository;

    public ResponseDto<?> interestService(Long clubId, Member member) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NullPointerException("해당 모임이 존재하지 않습니다."));



        if (!interestRepository.existsByClubAndMember(club,member)) {

            Interest interest = new Interest(club,member);

            interestRepository.save(interest);

            return ResponseDto.success("관심모임 등록 완료.");
        }

        Interest interest = interestRepository.findByClubAndMember(club,member);

        interestRepository.delete(interest);

        return ResponseDto.success("관심 취소");
    }
}
