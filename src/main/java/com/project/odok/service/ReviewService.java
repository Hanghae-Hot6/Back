package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.review.ReviewRequestDto;
import com.project.odok.dto.responseDto.ClubReviewResponseDto;
import com.project.odok.dto.responseDto.ReviewResponseDto;
import com.project.odok.entity.Club;
import com.project.odok.entity.Member;
import com.project.odok.entity.Review;
import com.project.odok.repository.ClubRepository;
import com.project.odok.repository.MemberRepository;
import com.project.odok.repository.ReviewRepository;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.security.exception.ErrorCode;
import com.project.odok.security.exception.OdokExceptions;
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


    public ResponseDto<?> getAllReview(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new OdokExceptions(ErrorCode.NOT_FOUND_CLUB));

        List<Review> reviewList = reviewRepository.findAllByClubOrderByCreatedAtDesc(club);

        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            reviewResponseDtoList.add(new ReviewResponseDto(review));
        }

        return ResponseDto.success(reviewResponseDtoList);
    }

    public ResponseDto<?> createReview(Long clubId, UserDetailsImpl userDetails, ReviewRequestDto reviewRequestDto) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new OdokExceptions(ErrorCode.NOT_FOUND_CLUB));

        Member member = memberRepository.findById(userDetails.getMember().getMemberId()).orElseThrow(() -> new OdokExceptions(ErrorCode.NOT_FOUND_MEMBER));

        Review review = new Review(member, club, reviewRequestDto);
        reviewRepository.save(review);

        return ResponseDto.success("?????? ?????? ??????");
    }

    public ResponseDto<?> deleteReview(Long reviewId, UserDetailsImpl userDetails) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new OdokExceptions(ErrorCode.NOT_FOUND_REVIEW));

        if (review.getMember().getMemberId().equals(userDetails.getMember().getMemberId())) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new OdokExceptions(ErrorCode.INVALID_WRITER);
        }

        return ResponseDto.success("????????? ?????? ???????????????.");
    }

    // ???????????? ?????? ??????
    public ResponseDto<?> getReviewList() {

        List<Club> clubList = clubRepository.findAllByOrderByFinishDateAsc();
        List<ClubReviewResponseDto> clubReviewList = new ArrayList<>();

        for (Club club : clubList) {
            List<Review> reviewList = reviewRepository.findAllByClubOrderByCreatedAtDesc(club);

            List<ReviewResponseDto> reviewResponseList = new ArrayList<>();

            for (Review review : reviewList) {
                reviewResponseList.add(new ReviewResponseDto(review));
            }

            if (reviewList.size() == 0) continue;

            clubReviewList.add(new ClubReviewResponseDto(club, reviewResponseList));

            if (clubReviewList.size() == 3)
                return ResponseDto.success(clubReviewList);
        }

        return ResponseDto.success(clubReviewList);
    }
}