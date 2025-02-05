package com.mindata.w2m.spaceship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class SpaceshipRequestDTO {

    @JsonProperty("spaceship_name")
    @NotBlank(message = "[spaceship_name] es un campo obligatorio")
    private String spaceshipName;

    @JsonProperty("tv_program")
    @NotBlank(message = "[tv_program] es un campo obligatorio")
    private String tvProgram;

    @JsonProperty("capacity")
    @NotNull(message = "[capacity] es un campo obligatorio")
    @PositiveOrZero(message = "[capacity] debe ser mayor o igual a cero")
    private Integer capacity;

    public @NotBlank(message = "[spaceship_name] es un campo obligatorio") String getSpaceshipName() {
        return spaceshipName;
    }

    public void setSpaceshipName(@NotBlank(message = "[spaceship_name] es un campo obligatorio") String spaceshipName) {
        this.spaceshipName = spaceshipName;
    }

    public @NotBlank(message = "[tv_program] es un campo obligatorio") String getTvProgram() {
        return tvProgram;
    }

    public void setTvProgram(@NotBlank(message = "[tv_program] es un campo obligatorio") String tvProgram) {
        this.tvProgram = tvProgram;
    }

    public @NotNull(message = "[capacity] es un campo obligatorio") @PositiveOrZero(message = "[capacity] debe ser mayor o igual a cero") Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull(message = "[capacity] es un campo obligatorio") @PositiveOrZero(message = "[capacity] debe ser mayor o igual a cero") Integer capacity) {
        this.capacity = capacity;
    }
}
