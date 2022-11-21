package com.project.odok.repository;

import com.project.odok.entity.Club;
import com.project.odok.entity.Interest;
import com.project.odok.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    Boolean existsByMemberAndClub(Member member, Club club);
    Interest findByMemberAndClub(Member member, Club club);

    List<Interest> findAllByMember(Member member);
}
