package com.project.odok.repository;

import com.project.odok.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findAllByChatRoomIdOrderByDateDesc(String roomNo, Pageable pageable);

    Long countChatMessagesByChatRoomId(String chatRoomId);

    void deleteAllByChatRoomId(String chatRoomId);
}
