package com.project.odok.controller;

import com.project.odok.dto.ResponseDto;
import com.project.odok.security.UserDetailsImpl;
import com.project.odok.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;


    // 모임 참여한 모든 채팅방 조회
    @GetMapping("/rooms")
    public ResponseDto<?> findAllChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return chatRoomService.findAllChatRoom(userDetails.getMember());
    }

    // 특정 채팅방 조회

    @GetMapping("/room/{roomId}")
    public ResponseDto<?> findOneRoom(@PathVariable String roomId){
        return chatRoomService.findOneRoom(roomId);
    }

}
