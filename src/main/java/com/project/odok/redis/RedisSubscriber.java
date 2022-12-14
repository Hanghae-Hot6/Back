package com.project.odok.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.odok.dto.responseDto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            MessageResponseDto responseDto = objectMapper.readValue(publishMessage, MessageResponseDto.class);
            messagingTemplate.convertAndSend("/sub/chat/messages/" + responseDto.getChatRoomId(), responseDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
