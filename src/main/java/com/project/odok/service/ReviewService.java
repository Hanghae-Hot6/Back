package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.review.ReviewRequestDto;
import com.project.odok.dto.responseDto.ClubReviewResponseDto;
import com.project.odok.dto.responseDto.ClubsInfoResponseDto;
import com.project.odok.dto.responseDto.ReviewResponseDto;
import com.project.odok.entity.Club;
import com.project.odok.entity.Member;
import com.project.odok.entity.Review;
import com.project.odok.repository.ClubRepository;
import com.project.odok.repository.MemberRepository;
import com.project.odok.repository.ReviewRepository;
import com.project.odok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;


    public ResponseDto<?> getAllReview (Long clubId){
        Club club = clubRepository.findById(clubId).orElseThrow(
                () -> new RuntimeException("해당 모임이 존재하지 않습니다.")
        );

        List<Review> reviewList = reviewRepository.findAllByClubOrderByCreatedAtDesc(club);

        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            reviewResponseDtoList.add(new ReviewResponseDto(review));
        }

        return ResponseDto.success(reviewResponseDtoList);
    }

    public ResponseDto<?> createdReivew (Long clubId, UserDetailsImpl userDetails, ReviewRequestDto reviewRequestDto){

        Club club = clubRepository.findById(clubId).orElseThrow(
                () -> new RuntimeException("해당 모임은 존재하지 않습니다.")
        );

        Member member = memberRepository.findById(userDetails.getMember().getMemberId()).orElseThrow(
                () -> new RuntimeException("아이디가 존재하지 않습니다. 회원 가입 후 리뷰 등록을 해주시기 바랍니다.")
        );

        Review review = new Review(member, club, reviewRequestDto);
        reviewRepository.save(review);

        return ResponseDto.success("리뷰 등록 완료");
    }

    public ResponseDto<?> deleteReview (Long reviewId, UserDetailsImpl userDetails){

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("해당 리뷰가 존재하지 않습니다.")
        );

        if(review.getMember().getMemberId().equals(userDetails.getMember().getMemberId())){
            reviewRepository.deleteById(reviewId);
        } else {
            throw new RuntimeException("해당 리뷰 작성자가 아니므로 삭제 실패");
        }

        return ResponseDto.success("리뷰가 삭제 되었습니다.");
    }

    public ResponseDto<?> getReviewList() {

        List<Club> clubList = clubRepository.findTop3ByOrderByCreatedAtAsc();
        List<ClubReviewResponseDto> clubReviewList = new ArrayList<>();

        for (Club club : clubList){
            List<Review> reviewList = reviewRepository.findAllByClubOrderByCreatedAtDesc(club);

            List<ReviewResponseDto> reviewResponseList = new ArrayList<>();

            for (Review review : reviewList) {
                reviewResponseList.add(new ReviewResponseDto(review));
            }

            clubReviewList.add(new ClubReviewResponseDto(club,reviewResponseList));
        }

        return ResponseDto.success(clubReviewList);
    }
}
