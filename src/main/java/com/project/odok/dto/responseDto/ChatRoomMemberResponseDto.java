package com.project.odok.dto.responseDto;

import com.project.odok.entity.ChatRoomMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomMemberResponseDto {

    private String chatRoomId;
    private String title;


    public ChatRoomMemberResponseDto(ChatRoomMember chatRoomMember){
        this.chatRoomId = chatRoomMember.getChatRoom().getChatRoomId();
        this.title = chatRoomMember.getChatRoom().getTitle();
    }
}
