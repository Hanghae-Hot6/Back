package com.project.odok.controller;

import com.project.odok.dto.requestDto.chat.MessageRequestDto;
import com.project.odok.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;


    @MessageMapping("/chat/message")
    public void sendMessage(MessageRequestDto requestDto) {
        chatMessageService.sendMessage(requestDto);
    }

}
