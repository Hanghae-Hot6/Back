package com.project.odok.entity;

import com.project.odok.dto.requestDto.review.ReviewRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Review extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubId")
    private Club club;

    @Column
    private String comment;

    public Review (Member member, Club club, ReviewRequestDto reviewRequestDto) {
        this.member = member;
        this.club = club;
        this. comment = reviewRequestDto.getComment();
    }
}
