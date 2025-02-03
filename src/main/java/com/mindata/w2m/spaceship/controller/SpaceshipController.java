package com.mindata.w2m.spaceship.controller;

import com.mindata.w2m.spaceship.dto.SpaceshipRequestDTO;
import com.mindata.w2m.spaceship.dto.SpaceshipResponseDTO;
import com.mindata.w2m.spaceship.service.SpaceshipService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {

    private final SpaceshipService service;

    public SpaceshipController(SpaceshipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<SpaceshipResponseDTO>> getAllSpaceships(
            @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.findAllSpaceships(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceshipResponseDTO> getSpaceshipById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findSpaceshipById(id));
    }

    @GetMapping("/by-name/{name-part}")
    public ResponseEntity<Page<SpaceshipResponseDTO>> getSpaceshipsByNamePart(
            @PathVariable(value = "name-part") String namePart, @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.findSpaceshipsByNamePart(namePart, pageable));
    }

    @PostMapping
    public ResponseEntity<SpaceshipResponseDTO> saveSpaceship(@RequestBody @Valid SpaceshipRequestDTO requestDTO) {
        return ResponseEntity.ok(service.saveSpaceship(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceshipResponseDTO> updateSpaceship(@PathVariable Long id,
                                                                @RequestBody @Valid SpaceshipRequestDTO requestDTO) {
        return ResponseEntity.ok(service.updateSpaceship(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSpaceship(@PathVariable Long id) {
        service.deleteSpaceshipById(id);
        return ResponseEntity.ok().build();
    }

}
