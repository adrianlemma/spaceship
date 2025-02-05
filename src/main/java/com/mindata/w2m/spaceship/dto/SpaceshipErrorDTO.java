package com.mindata.w2m.spaceship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpaceshipErrorDTO {

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_message")
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public SpaceshipErrorDTO(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
