package com.project.odok.repository;

import com.project.odok.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByMemberId(String memberId);

}
