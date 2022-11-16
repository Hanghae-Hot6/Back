package com.project.odok.entity;

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

    public ClubBook(Club club, Book book1, Book book2, Book book3){
        this.club = club;
        this.book1 = book1;
        this.book2 = book2;
        this.book3 = book3;
    }

}