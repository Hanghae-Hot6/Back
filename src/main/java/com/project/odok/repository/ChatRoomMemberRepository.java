package com.project.odok.repository;

import com.project.odok.entity.ChatRoomMember;
import com.project.odok.entity.Club;
import com.project.odok.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    List<ChatRoomMember> findAllByMember(Member member);
    ChatRoomMember findByClubAndMember(Club club,Member member);

}