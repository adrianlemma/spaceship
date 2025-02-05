package com.mindata.w2m.spaceship.mq;

public interface ErrorLogConsumer {

    void receiveMessage(String message);

}
