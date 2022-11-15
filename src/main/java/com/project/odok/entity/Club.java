package com.project.odok.entity;

import com.project.odok.dto.requestDto.ClubRequestDto;
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
    @Column(nullable = false)
    private String clubName;
    @Column(nullable = false)
    private String clubIntro;
    @Column(nullable = false)
    private String plan;
    private String location;
    @Column(nullable = false)
    private String schedule;
    @Column(nullable = false)
    private Long memberLimit;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String summary;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubMember> clubMemberList;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubBook> clubBookList;


    public Club(ClubRequestDto clubRequestDto, Member member, S3UploadService s3UploadService, String dir) throws IOException {
        this.clubName = clubRequestDto.getClubName();
        this.clubIntro = clubRequestDto.getClubIntro();
        this.plan = clubRequestDto.getPlan();
        this.location = clubRequestDto.getLocation();
        this.schedule = clubRequestDto.getSchedule();
        this.memberLimit = clubRequestDto.getMemberLimit();
        this.category = clubRequestDto.getCategory();
        this.summary = clubRequestDto.getSummary();
        this.imageUrl = s3UploadService.upload(clubRequestDto.getImageUrl(), dir);
        this.member = member;
    }

    public void update(ClubRequestDto clubRequestDto, S3UploadService s3UploadService, String dir) throws IOException{
        this.clubName = clubRequestDto.getClubName();
        this.clubIntro = clubRequestDto.getClubIntro();
        this.plan = clubRequestDto.getPlan();
        this.location = clubRequestDto.getLocation();
        this.schedule = clubRequestDto.getSchedule();
        this.memberLimit = clubRequestDto.getMemberLimit();
        this.category = clubRequestDto.getCategory();
        this.summary = clubRequestDto.getSummary();
        this.imageUrl = s3UploadService.upload(clubRequestDto.getImageUrl(), dir);
    }
}
