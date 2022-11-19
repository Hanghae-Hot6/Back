package com.project.odok.entity;

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
    private String memberMinNum;  // 진짜 필요한건지 의문
    private String memberMaxNum;
    private String startDate;
    private String finishDate;
    private String location;
    private String schedule;
    @Column(columnDefinition = "TEXT")
    private String clubIntro;
    @Column(columnDefinition = "TEXT")
    private String clubSummary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubMember> clubMemberList;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubBook> clubBookList;


    public Club(ClubRequestDto clubRequestDto, Member member, S3UploadService s3UploadService, String dir) throws IOException{
        this.clubName = clubRequestDto.getClubName();
        this.thumbnail = clubRequestDto.getThumbnail() != null? s3UploadService.upload(clubRequestDto.getThumbnail(), dir) : null;
        this.category = clubRequestDto.getCategory();
        this.memberMinNum = clubRequestDto.getMemberMinNum();
        this.memberMaxNum = clubRequestDto.getMemberMaxNum();
        this.startDate = clubRequestDto.getStartDate();
        this.finishDate = clubRequestDto.getFinishDate();
        this.location = clubRequestDto.getLocation();
        this.schedule = clubRequestDto.getSchedule();
        this.clubIntro = clubRequestDto.getClubIntro();
        this.clubSummary = clubRequestDto.getClubSummary();
        this.member = member;
    }

    public void update(ClubRequestDto clubRequestDto, S3UploadService s3UploadService, String dir) throws IOException{
        this.clubName = clubRequestDto.getClubName();
        this.thumbnail = clubRequestDto.getThumbnail() != null? s3UploadService.upload(clubRequestDto.getThumbnail(), dir) : null;
        this.category = clubRequestDto.getCategory();
        this.memberMinNum = clubRequestDto.getMemberMinNum();
        this.memberMaxNum = clubRequestDto.getMemberMaxNum();
        this.startDate = clubRequestDto.getStartDate();
        this.finishDate = clubRequestDto.getFinishDate();
        this.location = clubRequestDto.getLocation();
        this.schedule = clubRequestDto.getSchedule();
        this.clubIntro = clubRequestDto.getClubIntro();
        this.clubSummary = clubRequestDto.getClubSummary();
    }
}