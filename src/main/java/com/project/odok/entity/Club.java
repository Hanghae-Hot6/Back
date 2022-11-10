package com.project.odok.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Club extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;
    private String clubName;
    private String clubIntro;
    private String plan;
    private String book1;
    private String book2;
    private String book3;
    private String location;
    private String schedule;
    private Long memberLimit;
    private String category;
    private String summary;
    private String imageUrl;
}
