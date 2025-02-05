package com.mindata.w2m.spaceship.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "spaceship", indexes = {
        @Index(name = "IDX_SPCACECHIP_UNQ", columnList = "spaceship_name, tv_program", unique = true)
})

@Entity
public class Spaceship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spaceship_name", nullable = false)
    private String spaceshipName;

    @Column(name = "tv_program", nullable = false)
    private String tvProgram;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @PrePersist
    @PreUpdate
    public void updateLastUpdate() {
        setLastUpdate(LocalDateTime.now());
    }

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
