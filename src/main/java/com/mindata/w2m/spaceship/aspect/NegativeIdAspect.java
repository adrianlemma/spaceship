package com.mindata.w2m.spaceship.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.mindata.w2m.spaceship.constant.ConstantValues.LOG_NEGATIVE_ID_MESSAGE;

@Aspect
@Component
public class NegativeIdAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(NegativeIdAspect.class);

    @Pointcut("execution(* com.mindata.w2m.spaceship.controller.SpaceshipController.getSpaceshipById(..)) && args(id,..)")
    public void spaceshipIdPointcut(Long id) {}

    @Before("spaceshipIdPointcut(id)")
    public void logIfNegativeId(JoinPoint joinPoint, Long id) {
        if (id < 0) {
            LOGGER.warn(LOG_NEGATIVE_ID_MESSAGE, id);
        }
    }

}
