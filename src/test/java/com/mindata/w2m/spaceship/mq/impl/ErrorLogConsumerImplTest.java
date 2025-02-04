package com.mindata.w2m.spaceship.mq.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.w2m.spaceship.dto.SpaceshipErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ErrorLogConsumerImplTest {

    @InjectMocks
    private ErrorLogConsumerImpl logConsumer;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void receiveMessageOK() throws JsonProcessingException {
        when(objectMapper.readValue(anyString(), eq(SpaceshipErrorDTO.class)))
                .thenReturn(new SpaceshipErrorDTO("code", "message"));
        logConsumer.receiveMessage("{\"error_code\":\"code\",\"error_message\":\"message\"}");
    }

    @Test
    void receiveMessageThrowException() throws JsonProcessingException {
        when(objectMapper.readValue(anyString(), eq(SpaceshipErrorDTO.class))).thenThrow(JsonProcessingException.class);
        logConsumer.receiveMessage("-");
        verify(objectMapper).readValue(anyString(), eq(SpaceshipErrorDTO.class));
    }

}