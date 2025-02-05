package com.mindata.w2m.spaceship.mq.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.w2m.spaceship.dto.SpaceshipErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ErrorLogProducerImplTest {

    @InjectMocks
    private ErrorLogProducerImpl logProducer;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private Queue queue;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessageOK() throws JsonProcessingException {
        when(queue.getName()).thenReturn("queue_name");
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString());
        when(objectMapper.writeValueAsString(any(SpaceshipErrorDTO.class)))
                .thenReturn("{\"error_code\":\"code\",\"error_message\":\"message\"}");
        logProducer.sendMessage(new SpaceshipErrorDTO("code", "message"));
        verify(rabbitTemplate).convertAndSend(anyString(), anyString());
    }

    @Test
    void sendMessageThrowException() throws JsonProcessingException {
        when(queue.getName()).thenReturn("queue_name");
        when(objectMapper.writeValueAsString(any(SpaceshipErrorDTO.class))).thenThrow(JsonProcessingException.class);
        logProducer.sendMessage(new SpaceshipErrorDTO("code", "message"));
        verifyNoInteractions(rabbitTemplate);
    }

}