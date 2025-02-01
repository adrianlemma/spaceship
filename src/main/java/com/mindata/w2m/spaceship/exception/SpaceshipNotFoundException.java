package com.mindata.w2m.spaceship.exception;

public class SpaceshipNotFoundException extends RuntimeException {

    private final String code;
    private final String description;

    public SpaceshipNotFoundException(String code, String description) {
        super(code + " - " + description);
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
