package com.project.odok.repository;

import com.project.odok.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByMemberId(String memberId);
    Optional<Member> findByUsername(String userName);

    Optional<Member> findByKakaoId(Long kakaoId);
    Optional<Member> findByEmail(String email);
}
