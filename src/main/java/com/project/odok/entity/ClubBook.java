package com.project.odok.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ClubBook extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
}
