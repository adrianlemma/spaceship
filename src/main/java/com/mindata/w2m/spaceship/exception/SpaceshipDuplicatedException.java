package com.mindata.w2m.spaceship.exception;

public class SpaceshipDuplicatedException extends RuntimeException {

    private final String code;
    private final String description;

    public SpaceshipDuplicatedException(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
