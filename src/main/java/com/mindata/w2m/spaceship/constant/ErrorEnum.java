package com.mindata.w2m.spaceship.constant;

public enum ErrorEnum {

    NOT_FOUND_BY_ID("ERR_001", "No se encontró nava espacial con id: %s"),
    DUPLICATED_SPACESHIP("ERR_002", "Ya existe una nave con spaceship_name: %s y tv_program: %s"),
    FIELD_VALIDATION("ERR_003", "Campos invalidos: %s"),
    GENERIC_EXCEPTION("ERR_004", "Excepcion no controlada, intente nuevamente.");

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
