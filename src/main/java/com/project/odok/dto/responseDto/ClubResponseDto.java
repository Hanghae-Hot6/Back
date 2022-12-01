package com.project.odok.dto.responseDto;

import com.project.odok.entity.Club;
import com.project.odok.entity.ClubBook;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubResponseDto {
    private Long clubId;
    private String thumbnail;
    private String clubName;
    private String clubIntro;
    private String category;
    private String location;
    private String period;
    private String leader;
    private String participantNum;
    private Boolean subscription;
    private Boolean interest;
    private String clubSummary;
    private String bookSummary;
    private String bookImage1;
    private String bookImage2;
    private String bookImage3;
    private String bookName1;
    private String bookName2;
    private String bookName3;
    private String bookLink1;
    private String bookLink2;
    private String bookLink3;
    private String schedule;
    private String book1;
    private String book2;
    private String book3;


    public ClubResponseDto(Club club, ClubBook clubBook, Boolean subscription, Boolean interest, String memberNum) {
        this.clubId = club.getClubId();
        this.thumbnail = club.getThumbnail();
        this.clubName = club.getClubName();
        this.clubIntro = club.getClubIntro();
        this.category = club.getCategory();
        this.location = club.getLocation();
        this.period = club.getStartDate() + " ~ " + club.getFinishDate();
        this.leader = club.getLeader().getMemberId();
        this.participantNum = memberNum + "/" + club.getMemberMaxNum(); // 쿼리문 작성해야됨.
        this.subscription = subscription;
        this.interest = interest;
        this.clubSummary = club.getClubSummary();
        this.bookSummary = clubBook.getBookSummary();
        this.bookName1 = clubBook.getBook1() != null ? clubBook.getBook1().getBookName() : "책을 선택하세요";
        this.bookName2 = clubBook.getBook2() != null ? clubBook.getBook2().getBookName() : "책을 선택하세요";
        this.bookName3 = clubBook.getBook3() != null ? clubBook.getBook3().getBookName() : "책을 선택하세요";
        this.bookImage1 = clubBook.getBook1() != null ? clubBook.getBook1().getBookImage() : "책을 선택하세요";
        this.bookImage2 = clubBook.getBook2() != null ? clubBook.getBook2().getBookImage() : "책을 선택하세요";
        this.bookImage3 = clubBook.getBook3() != null ? clubBook.getBook3().getBookImage() : "책을 선택하세요";
        this.bookLink1 = clubBook.getBook1() != null ? clubBook.getBook1().getLink() : "책을 선택하세요";
        this.bookLink2 = clubBook.getBook2() != null ? clubBook.getBook2().getLink() : "책을 선택하세요";
        this.bookLink3 = clubBook.getBook3() != null ? clubBook.getBook3().getLink() : "책을 선택하세요";
        this.schedule = club.getSchedule();
        this.book1 = clubBook.getBook1() != null ? clubBook.getBook1().getIsbn() : "책을 선택하세요";
        this.book2 = clubBook.getBook2() != null ? clubBook.getBook2().getIsbn() : "책을 선택하세요";
        this.book3 = clubBook.getBook3() != null ? clubBook.getBook3().getIsbn() : "책을 선택하세요";
    }

}