package com.mindata.w2m.spaceship.constant;

public enum ErrorEnum {

    NOT_FOUND_BY_ID("ERR_001", "No se encontró nava espacial con id: %s");

    private String code;
    private String description;

    private ErrorEnum(String code, String description) {
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
