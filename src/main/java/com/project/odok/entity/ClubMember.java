package com.project.odok.entity;

import lombok.Getter;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Getter
@Entity
public class ClubMember extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
