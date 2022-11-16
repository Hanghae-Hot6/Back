package com.project.odok.dto.responseDto;
import com.project.odok.entity.Club;
import com.project.odok.entity.ClubBook;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubResponseDto {
    private Long clubId;
    private String leader;
    private String clubName;
    private String clubIntro;
    private String plan;
    private String location;
    private String schedule;
    private String memberLimit;
    private String category;
    private String summary;
    private String imageUrl;
    private String bookImage1;
    private String bookImage2;
    private String bookImage3;
    private String bookName1;
    private String bookName2;
    private String bookName3;
    private String bookLink1;
    private String bookLink2;
    private String bookLink3;


    public ClubResponseDto(Club club, ClubBook clubBook){
        this.clubId = club.getClubId();
        this.leader = club.getMember().getMemberId();
        this.clubName = club.getClubName();
        this.clubIntro = club.getClubIntro();
        this.plan = club.getPlan();
        this.location = club.getLocation();
        this.schedule = club.getSchedule();
        this.memberLimit = club.getMemberLimit();
        this.category = club.getCategory();
        this.summary = club.getSummary();
        this.imageUrl = club.getImageUrl();
        this.bookName1 = clubBook.getBook1().getBookName();
        this.bookName2 = clubBook.getBook2().getBookName();
        this.bookName3 = clubBook.getBook3().getBookName();
        this.bookImage1 = clubBook.getBook1().getBookImage();
        this.bookImage2 = clubBook.getBook2().getBookImage();
        this.bookImage3 = clubBook.getBook3().getBookImage();
        this.bookLink1 = clubBook.getBook1().getLink();
        this.bookLink2 = clubBook.getBook2().getLink();
        this.bookLink3 = clubBook.getBook3().getLink();
    }

}
