package com.project.odok.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom extends TimeStamped{

    @Id
    private String chatRoomId;

    @Column(nullable = false)
    private String title;


    public ChatRoom(String chatRoomId, String title){
        this.chatRoomId = chatRoomId;
        this.title = title;
    }

    public String chatDate(){
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일 a hh:mm:ss", Locale.KOREA));
        return date;
    }
}
