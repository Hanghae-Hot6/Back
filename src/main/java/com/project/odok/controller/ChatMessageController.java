package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.chat.MessageRequestDto;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @GetMapping("/chat/messages/{roomNo}")
    public ResponseDto<?> getMessages(@PathVariable String roomNo,
                                      @RequestParam("page") int page,
                                      @RequestParam("size") int size) {
        int pageTemp = page - 1;

        return chatMessageService.getMessages(roomNo, pageTemp, size);
    }

    @MessageMapping("/chat/message")
    public void sendMessage(MessageRequestDto requestDto) {
        String temp = "/sub/chat/messages/" + requestDto.getChatRoomId();
        simpMessageSendingOperations.convertAndSend( temp, chatMessageService.sendMessage(requestDto));
    }
}
