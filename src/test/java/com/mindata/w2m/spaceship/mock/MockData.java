package com.mindata.w2m.spaceship.mock;

import com.mindata.w2m.spaceship.dto.SpaceshipRequestDTO;
import com.mindata.w2m.spaceship.dto.SpaceshipResponseDTO;
import com.mindata.w2m.spaceship.model.Spaceship;

import java.time.LocalDateTime;

public class MockData {

    public static Spaceship mockSpaceship() {
        Spaceship spaceship = new Spaceship();
        spaceship.setId(1L);
        spaceship.setSpaceshipName("spaceship");
        spaceship.setTvProgram("tv-program");
        spaceship.setCapacity(10);
        spaceship.updateLastUpdate();
        return spaceship;
    }

    public static SpaceshipRequestDTO mockSpaceshipRequest() {
        SpaceshipRequestDTO spaceshipRequest = new SpaceshipRequestDTO();
        spaceshipRequest.setSpaceshipName("spaceship");
        spaceshipRequest.setTvProgram("tv-program");
        spaceshipRequest.setCapacity(10);
        return spaceshipRequest;
    }

    public static SpaceshipResponseDTO mockSpaceshipResponse() {
        SpaceshipResponseDTO spaceshipResponse = new SpaceshipResponseDTO();
        spaceshipResponse.setId(1L);
        spaceshipResponse.setSpaceshipName("spaceship");
        spaceshipResponse.setTvProgram("tv-program");
        spaceshipResponse.setCapacity(10);
        spaceshipResponse.setLastUpdate(LocalDateTime.now());
        return spaceshipResponse;
    }

}
