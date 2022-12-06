package com.project.odok.repository;

import com.project.odok.entity.Club;
import com.project.odok.entity.Member;
import com.project.odok.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findAllByOrderByCreatedAtDesc();
    List<Club> findAllByOrderByFinishDateAsc();
    List<Club> findTop5ByOrderByVisitNumDesc();
    Page<Club> findAllByClubNameContainsOrderByVisitNumDesc(String clubName, Pageable pageable);
    List<Club> findAllByLeader(Member member);
}