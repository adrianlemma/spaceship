package com.mindata.w2m.spaceship.service;

import com.mindata.w2m.spaceship.dto.SpaceshipRequestDTO;
import com.mindata.w2m.spaceship.dto.SpaceshipResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpaceshipService {

    Page<SpaceshipResponseDTO> findAllSpaceships(Pageable pageable);
    SpaceshipResponseDTO findSpaceshipById(Long id);
    Page<SpaceshipResponseDTO> findSpaceshipsByNamePart(String namePart, Pageable pageable);
    SpaceshipResponseDTO saveSpaceship(SpaceshipRequestDTO requestDTO);
    SpaceshipResponseDTO updateSpaceship(Long id, SpaceshipRequestDTO requestDTO);
    void deleteSpaceshipById(Long id);

}
