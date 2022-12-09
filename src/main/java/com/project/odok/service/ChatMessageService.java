package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.chat.MessageRequestDto;
import com.project.odok.dto.responseDto.MessageResponseDto;
import com.project.odok.entity.ChatMessage;
import com.project.odok.repository.*;
import com.project.odok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ResponseDto<?> getMessages(UserDetailsImpl userDetails, String roomNo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChatMessage> messageList = chatMessageRepository.findAllByChatRoomId(roomNo, pageable);
        List<MessageResponseDto> messageResponseDtoList = new ArrayList<>();
        for (ChatMessage chatMessage : messageList) {
            messageResponseDtoList.add(MessageResponseDto.builder()
                    .chatRoomId(chatMessage.getChatRoomId())
                    .type(chatMessage.getType())
                    .sender(chatMessage.getSender())
                    .message(chatMessage.getMessage())
                    .date(chatMessage.getDate())
                    .build()
            );
        }

        return ResponseDto.success(messageResponseDtoList);
    }

    public Object sendMessage(MessageRequestDto requestDto){
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일 a hh:mm:ss", Locale.KOREA));
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(requestDto.getChatRoomId())
                .type(requestDto.getType())
                .sender(requestDto.getSender())
                .message(requestDto.getMessage())
                .date(date)
                .build();

        chatMessageRepository.save(chatMessage);

        MessageResponseDto responseDto = MessageResponseDto.builder()
                .chatRoomId(chatMessage.getChatRoomId())
                .type(chatMessage.getType())
                .sender(chatMessage.getSender())
                .message(chatMessage.getMessage())
                .date(chatMessage.getDate())
                .build();

        return responseDto;
    }

}
