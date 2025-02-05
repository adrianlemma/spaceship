package com.mindata.w2m.spaceship.mq.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.w2m.spaceship.dto.SpaceshipErrorDTO;
import com.mindata.w2m.spaceship.mq.ErrorLogConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.mindata.w2m.spaceship.constant.ConstantValues.*;

@Component
public class ErrorLogConsumerImpl implements ErrorLogConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorLogConsumerImpl.class);
    private final ObjectMapper objectMapper;

    public ErrorLogConsumerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @RabbitListener(queues = {"${error.log.queue.name}"})
    public void receiveMessage(@Payload String message) {
        try {
            SpaceshipErrorDTO errorLog = objectMapper.readValue(message, SpaceshipErrorDTO.class);
            LOGGER.warn(String.format(LOG_ERROR_MESSAGE, errorLog.getErrorCode(), errorLog.getErrorMessage()));
        } catch (IOException ex) {
            LOGGER.error(ERROR_STRING_TO_MESSAGE);
        }
    }

}
