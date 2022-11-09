package com.project.odok.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clubName;
    private String clubIntro;

    @OneToOne
    private Book book;
    private String plan;
    private String location;
    private String schedule;
    private Long memberLimit;
    private String category;
    private String summary;
    private String imageUrl;
}
