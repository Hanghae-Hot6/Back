package com.project.odok.service;

import com.project.odok.dto.requestDto.chat.MessageRequestDto;
import com.project.odok.dto.responseDto.MessageResponseDto;
import com.project.odok.entity.ChatMessage;
import com.project.odok.entity.ChatRoom;
import com.project.odok.entity.ChatRoomMember;
import com.project.odok.entity.Member;
import com.project.odok.redis.RedisPublisher;
import com.project.odok.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final RedisPublisher redisPublisher;



    public void sendMessage(MessageRequestDto requestDto){
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일 a hh:mm:ss", Locale.KOREA));

        if(requestDto.getType().equals("ENTER")){
            Member sender = memberRepository.findById(requestDto.getSender()).orElseThrow(
                    () -> new RuntimeException("해당 아이다가 존재하지 않습니다."));
            ChatRoom chatRoom = chatRoomRepository.findById(requestDto.getChatRoomId()).orElseThrow(
                    () -> new RuntimeException("해당 채팅방이 존재하지 않습니다."));;
            ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByMemberAndChatRoom(sender, chatRoom);


            if(!chatRoomMember.getEnterStatus()){
                String roomNo = String.valueOf(chatRoom.getChatRoomId());
                chatRoomRedisRepository.enterChatRoom(roomNo);

                ChatMessage chatMessage = ChatMessage.builder()
                        .chatRoomId(requestDto.getChatRoomId())
                        .type(requestDto.getType())
                        .sender(requestDto.getSender())
                        .message(requestDto.getSender() + "님이 입장하였습니다 :)")
                        .date(date)
                        .build();

                chatMessageRepository.save(chatMessage);

                chatRoomMember.updateEnterStatus();

                MessageResponseDto responseDto = MessageResponseDto.builder()
//                        .messageNo(chatMessage.getMessageNo())
                        .chatRoomId(chatMessage.getChatRoomId())
                        .type(chatMessage.getType())
                        .sender(chatMessage.getSender())
                        .message(chatMessage.getMessage())
                        .date(chatMessage.getDate())
                        .build();

                redisPublisher.publish(chatRoomRedisRepository.getTopic(roomNo), responseDto);
            }


        } else if(requestDto.getType().equals("TALK")){
            ChatMessage chatMessage = ChatMessage.builder()
                    .chatRoomId(requestDto.getChatRoomId())
                    .type(requestDto.getType())
                    .sender(requestDto.getSender())
                    .message(requestDto.getMessage())
                    .date(date)
                    .build();

            chatMessageRepository.save(chatMessage);

            MessageResponseDto responseDto = MessageResponseDto.builder()
//                    .messageNo(chatMessage.getMessageNo())
                    .chatRoomId(chatMessage.getChatRoomId())
                    .type(chatMessage.getType())
                    .sender(chatMessage.getSender())
                    .message(chatMessage.getMessage())
                    .date(chatMessage.getDate())
                    .build();

            String roomNo = String.valueOf(responseDto.getChatRoomId());
            redisPublisher.publish(chatRoomRedisRepository.getTopic(roomNo), responseDto);
        }
    }

}
