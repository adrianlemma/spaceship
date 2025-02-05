package com.mindata.w2m.spaceship.aspect;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NegativeIdAspectTest {

    @Mock
    private JoinPoint joinPoint;

    @InjectMocks
    private NegativeIdAspect aspect;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAspect() {
        aspect.spaceshipIdPointcut(1L);
        aspect.logIfNegativeId(joinPoint, -1L);
    }

}
