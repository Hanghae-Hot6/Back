package com.project.odok.dto.requestDto.chat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MessageRequestDto {

    private String chatRoomId;
    private String message;
    private String type;
    private String sender;
}
