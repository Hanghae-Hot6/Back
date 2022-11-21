package com.project.odok.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageResponseDto {
    private String chatRoomId;
    private String type;
    private String sender;
    private String message;
    private String date;

    @Builder
    public MessageResponseDto(String chatRoomId, String type, String sender, String message, String date){
        this.chatRoomId = chatRoomId;
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

}
