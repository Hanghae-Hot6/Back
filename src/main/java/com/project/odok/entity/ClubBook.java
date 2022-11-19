package com.project.odok.entity;

import com.project.odok.dto.requestDto.club.ClubRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ClubBook extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book1;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book2;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book3;
    private String bookIntro;
    private String bookSummary;

    public ClubBook(Club club, Book book1, Book book2, Book book3, ClubRequestDto clubRequestDto){
        this.club = club;
        this.book1 = book1;
        this.book2 = book2;
        this.book3 = book3;
        this.bookIntro = clubRequestDto.getBookIntro();
        this.bookSummary = clubRequestDto.getBookSummary();
    }

}
