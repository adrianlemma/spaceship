package com.mindata.w2m.spaceship.mq.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.w2m.spaceship.dto.SpaceshipErrorDTO;
import com.mindata.w2m.spaceship.mq.ErrorLogProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.mindata.w2m.spaceship.constant.ConstantValues.ERROR_MESSAGE_TO_STRING;

@Component
@EnableRabbit
public class ErrorLogProducerImpl implements ErrorLogProducer {

    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorLogProducerImpl.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper objectMapper;

    public ErrorLogProducerImpl(RabbitTemplate rabbitTemplate, Queue queue, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendMessage(SpaceshipErrorDTO errorLogDTO) {
        try {
            rabbitTemplate.convertAndSend(queue.getName(), objectMapper.writeValueAsString(errorLogDTO));
        } catch (AmqpException | JsonProcessingException ex) {
            LOGGER.error(ERROR_MESSAGE_TO_STRING, ex);
        }
    }

}
