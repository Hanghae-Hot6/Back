package com.project.odok.repository;

import com.project.odok.entity.Club;
import com.project.odok.entity.Interest;
import com.project.odok.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    Boolean existsByClubAndMember(Club club, Member member);
    Interest findByClubAndMember(Club club, Member member);
}
