package com.project.odok.service;

import com.project.odok.dto.requestDto.chat.MessageRequestDto;
import com.project.odok.entity.ChatMessage;
import com.project.odok.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void sendMessage(MessageRequestDto requestDto){
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일 a hh:mm:ss", Locale.KOREA));

        if(requestDto.getType().equals("ENTER")){
//            ChatRoom chatRoom
//            ChatRoomMember chatRoomMember = chatRoomService.getChatRoomByMemberAndChatRoom(sender, chatRoom);

            ChatMessage chatMessage = ChatMessage.builder()
                    .chatRoomId(requestDto.getChatRoomId())
                    .type(requestDto.getType())
                    .sender(requestDto.getSender())
                    .message(requestDto.getSender() + "님이 입장하였습니다 :)")
                    .date(date)
                    .build();

//            chatRoomMember.updateEnterStatus();


        } else if(requestDto.getType().equals("TALK")){
            ChatMessage chatMessage = ChatMessage.builder()
                    .chatRoomId(requestDto.getChatRoomId())
                    .type(requestDto.getType())
                    .sender(requestDto.getSender())
                    .message(requestDto.getMessage())
                    .date(date)
                    .build();
        }
    }

}
