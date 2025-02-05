package com.mindata.w2m.spaceship.constant;

public enum ErrorEnum {

    NOT_FOUND_BY_ID("ERR_001", "No se encontró nave espacial con id: %s"),
    DUPLICATED_SPACESHIP("ERR_002", "Ya existe una nave con spaceship_name: %s y tv_program: %s"),
    FIELD_VALIDATION("ERR_003", "Campos invalidos: %s"),
    INVALID_VARIABLE("ERR_004", "El formato de la variable: %s es invalido"),
    GENERIC_EXCEPTION("ERR_005", "Excepcion no controlada, intente nuevamente"),
    UNAUTHORIZED_ERROR("ERR_006", "Esta accion requiere ingresar un user y password validos"),
    FORBIDDEN_ERROR("ERR_007", "Esta accion debe realizarce con un user y password autorizado");

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
