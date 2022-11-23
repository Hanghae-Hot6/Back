package com.project.odok.dto.responseDto;

import com.project.odok.entity.ChatRoomMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomMemberResponseDto {

    private String chatRoomId;
    private String thumbnail;
    private String clubName;
    private int participants;

    public ChatRoomMemberResponseDto(ChatRoomMember chatRoomMember){
        this.chatRoomId = chatRoomMember.getChatRoom().getChatRoomId();
        this.thumbnail = chatRoomMember.getClub().getThumbnail();
        this.clubName = chatRoomMember.getClub().getClubName();
        this.participants = chatRoomMember.getChatRoom().getMemberList().size();
    }
}
