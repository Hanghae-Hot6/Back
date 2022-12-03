package com.project.odok.repository;


import com.project.odok.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    ChatRoom findByChatRoomIdAndTitle(String chatRoomId, String title);
}