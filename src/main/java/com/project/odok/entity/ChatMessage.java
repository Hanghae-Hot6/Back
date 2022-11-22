package com.project.odok.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column
    private String chatRoomId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String sender;

    @Column
    private String message;

    @Column
    private String date;

    @Builder
    public ChatMessage(String chatRoomId, String type, String sender, String message, String date){
        this.chatRoomId = chatRoomId;
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

}
