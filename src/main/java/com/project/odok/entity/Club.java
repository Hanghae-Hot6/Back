package com.project.odok.entity;

import com.project.odok.dto.requestDto.club.ClubRequestDto;
import com.project.odok.service.S3UploadService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    @Column(columnDefinition = "TEXT")
    private String clubIntro;

    private String plan;

    private String location;

    private String schedule;

    private String memberLimit;

    private String category;
    @Column(columnDefinition = "TEXT")
    private String summary;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
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

    public void update(ClubRequestDto clubRequestDto, MultipartFile imageUrl, S3UploadService s3UploadService, String dir) throws IOException{
        this.clubName = clubRequestDto.getClubName();
        this.clubIntro = clubRequestDto.getClubIntro();
        this.plan = clubRequestDto.getPlan();
        this.location = clubRequestDto.getLocation();
        this.schedule = clubRequestDto.getSchedule();
        this.memberLimit = clubRequestDto.getMemberLimit();
        this.category = clubRequestDto.getCategory();
        this.summary = clubRequestDto.getSummary();
        this.imageUrl = s3UploadService.upload(imageUrl, dir);
    }
}