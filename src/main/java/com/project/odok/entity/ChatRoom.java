package com.project.odok.entity;

import com.project.odok.dto.requestDto.club.ClubRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom extends TimeStamped{

    @Id
    private String chatRoomId;

    @Column(nullable = false)
    private String title;


    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatRoomMember> memberList;

    public ChatRoom(String chatRoomId, String title){
        this.chatRoomId = chatRoomId;
        this.title = title;
    }

    public void update(ClubRequestDto clubRequestDto){
        this.title = clubRequestDto.getClubName();
    }

    public String chatDate(){
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일 a hh:mm:ss", Locale.KOREA));
        return date;
    }
}