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
    private Boolean subscription;


    public ClubResponseDto(Club club, ClubBook clubBook, Boolean subscription){
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
        this.subscription = subscription;
        this.bookName1 = clubBook.getBook1() != null ? clubBook.getBook1().getBookName() : "책을 선택하세요";
        this.bookName2 = clubBook.getBook2() != null ? clubBook.getBook2().getBookName() : "책을 선택하세요";
        this.bookName3 = clubBook.getBook3() != null ? clubBook.getBook3().getBookName() : "책을 선택하세요";
        this.bookImage1 = clubBook.getBook1() != null ? clubBook.getBook1().getBookImage() : "책을 선택하세요";
        this.bookImage2 = clubBook.getBook2() != null ? clubBook.getBook2().getBookImage() : "책을 선택하세요";
        this.bookImage3 = clubBook.getBook3() != null ? clubBook.getBook3().getBookImage() : "책을 선택하세요";
        this.bookLink1 = clubBook.getBook1() != null ? clubBook.getBook1().getLink() : "책을 선택하세요";
        this.bookLink2 = clubBook.getBook2() != null ? clubBook.getBook2().getLink() : "책을 선택하세요";
        this.bookLink3 = clubBook.getBook3() != null ? clubBook.getBook3().getLink() : "책을 선택하세요";
    }

}