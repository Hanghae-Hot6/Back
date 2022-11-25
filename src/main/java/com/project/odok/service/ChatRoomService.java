package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.responseDto.ChatRoomMemberResponseDto;
import com.project.odok.entity.*;
import com.project.odok.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;


    // 모임 개설 시 채팅방 개설
    public void createdMemberChatRoom(Member member, Club club) {

        String createdChatRoomId = UUID.randomUUID().toString();

        ChatRoom createdChatRoom = new ChatRoom(createdChatRoomId, club.getClubName());
        chatRoomRepository.save(createdChatRoom);

        ChatRoomMember chatRoomMember = new ChatRoomMember(createdChatRoom, member, club, false);
        chatRoomMemberRepository.save(chatRoomMember);

        ChatMessage chatMessage = new ChatMessage(createdChatRoomId
                , "SYSTEM"
                , "SYSTEM"
                , "채팅방 대화가 시작되었습니다 :)"
                , createdChatRoom.chatDate());

        chatMessageRepository.save(chatMessage);
    }


    // 모임 가입 시 채팅방에 추가
    public void addMemberChatRoom(Member member, Club club) {

        ChatRoomMember getChatRoom = chatRoomMemberRepository.findByClubAndMember(club, club.getLeader());

        String chatRoomId = getChatRoom.getChatRoom().getChatRoomId();
        ChatRoom chatRoom = getChatRoom.getChatRoom();

        ChatRoomMember chatRoomMember = new ChatRoomMember(chatRoom, member, club, false);
        chatRoomMemberRepository.save(chatRoomMember);

        ChatMessage chatMessage = new ChatMessage(chatRoomId
                , "SYSTEM"
                , "SYSTEM"
                , member.getMemberId() + "님이 참여 하였습니다. :)"
                , chatRoom.chatDate());

        chatMessageRepository.save(chatMessage);
    }

    // 모임 탈퇴 시 채팅방 삭제
    public void deleteMemberChatRoom(Member member, Club club) {

        ChatRoomMember deleteChatRoomMember = chatRoomMemberRepository.findByClubAndMember(club, member);
        chatRoomMemberRepository.delete(deleteChatRoomMember);

    }


    // 모임 참여한 모든 채팅방 조회
    public ResponseDto<?> findAllChatRoom(Member member) {

        List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findAllByMember(member);

        List<ChatRoomMemberResponseDto> chatRoomMemberResponseDtoList = new ArrayList<>();

        for (ChatRoomMember chatRoomMember : chatRoomMemberList) {
            chatRoomMemberResponseDtoList.add(new ChatRoomMemberResponseDto(chatRoomMember));
        }

        return ResponseDto.success(chatRoomMemberResponseDtoList);
    }

    // 특정 채팅방 조회
    public ResponseDto<?> findOneRoom(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new RuntimeException("해당 채팅방이 존재하지 않습니다.")
        );
        return ResponseDto.success(chatRoom.getTitle() + " 채팅방이 조회 되었습니다.");
    }


}