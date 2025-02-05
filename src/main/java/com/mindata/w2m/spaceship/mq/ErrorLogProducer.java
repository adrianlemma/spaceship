package com.mindata.w2m.spaceship.mq;

import com.mindata.w2m.spaceship.dto.SpaceshipErrorDTO;

public interface ErrorLogProducer {

    void sendMessage(SpaceshipErrorDTO errorLogDTO);

}
