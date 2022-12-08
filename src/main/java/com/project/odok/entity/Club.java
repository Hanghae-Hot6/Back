package com.project.odok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.odok.dto.requestDto.club.ClubRequestDto;
import com.project.odok.service.S3UploadService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Club extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;
    private String clubName;
    private String category;
    private String thumbnail;
    private String memberMaxNum;
    private String startDate;
    private String finishDate;
    private String location;
    private String schedule;
    @Column(columnDefinition = "TEXT")
    private String clubIntro;
    @Column(columnDefinition = "TEXT")
    private String clubSummary;
    private long visitNum = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    private Member leader;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubMember> clubMemberList;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubBook> clubBookList;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Interest> clubInterestList;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ChatRoomMember> chatRoomMemberList;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Review> reviewList;


    public Club(ClubRequestDto clubRequestDto, Member member, S3UploadService s3UploadService, String dir) throws IOException{
        this.clubName = clubRequestDto.getClubName();
        this.thumbnail = clubRequestDto.getThumbnail() != null? s3UploadService.upload(clubRequestDto.getThumbnail(), dir, clubRequestDto.getClubName()) : "https://odok-s3.s3.ap-northeast-2.amazonaws.com//odok/defaultImage-dkckasdklnbeijnekbnprkdo";
        this.category = clubRequestDto.getCategory();
        this.memberMaxNum = clubRequestDto.getMemberMaxNum();
        this.startDate = clubRequestDto.getStartDate();
        this.finishDate = clubRequestDto.getFinishDate();
        this.location = clubRequestDto.getLocation();
        this.schedule = clubRequestDto.getSchedule();
        this.clubIntro = clubRequestDto.getClubIntro();
        this.clubSummary = clubRequestDto.getClubSummary();
        this.leader = member;
    }

    public void update(ClubRequestDto clubRequestDto, S3UploadService s3UploadService, String dir) throws IOException{
        this.clubName = clubRequestDto.getClubName();
        this.thumbnail = clubRequestDto.getThumbnail() != null? s3UploadService.upload(clubRequestDto.getThumbnail(), dir,clubRequestDto.getClubName()) : clubRequestDto.getBeforeThumbnail();
        this.category = clubRequestDto.getCategory();
        this.memberMaxNum = clubRequestDto.getMemberMaxNum();
        this.startDate = clubRequestDto.getStartDate();
        this.finishDate = clubRequestDto.getFinishDate();
        this.location = clubRequestDto.getLocation();
        this.schedule = clubRequestDto.getSchedule();
        this.clubIntro = clubRequestDto.getClubIntro();
        this.clubSummary = clubRequestDto.getClubSummary();
    }

    public void updateVisitCount(){
        this.visitNum += 1;
    }
}
