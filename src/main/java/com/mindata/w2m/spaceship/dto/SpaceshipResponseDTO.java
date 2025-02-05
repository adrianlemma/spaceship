package com.mindata.w2m.spaceship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class SpaceshipResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("spaceship_name")
    private String spaceshipName;

    @JsonProperty("tv_program")
    private String tvProgram;

    @JsonProperty("capacity")
    private Integer capacity;

    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpaceshipName() {
        return spaceshipName;
    }

    public void setSpaceshipName(String spaceshipName) {
        this.spaceshipName = spaceshipName;
    }

    public String getTvProgram() {
        return tvProgram;
    }

    public void setTvProgram(String tvProgram) {
        this.tvProgram = tvProgram;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
