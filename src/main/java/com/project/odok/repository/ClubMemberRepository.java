package com.project.odok.repository;

import com.project.odok.entity.Club;
import com.project.odok.entity.ClubMember;
import com.project.odok.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Boolean existsByMemberAndClub(Member member, Club club);
    ClubMember findByMemberAndClub(Member member, Club club);
}
