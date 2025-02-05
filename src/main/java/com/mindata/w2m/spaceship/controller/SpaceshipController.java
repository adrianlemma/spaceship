package com.mindata.w2m.spaceship.controller;

import com.mindata.w2m.spaceship.dto.SpaceshipRequestDTO;
import com.mindata.w2m.spaceship.dto.SpaceshipResponseDTO;
import com.mindata.w2m.spaceship.service.SpaceshipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {

    private final SpaceshipService service;

    public SpaceshipController(SpaceshipService service) {
        this.service = service;
    }

    @GetMapping
    @Tag(name = "Consultar naves espaciales", description = "Consulta con paginación (por defecto, de 20 registros)")
    public ResponseEntity<Page<SpaceshipResponseDTO>> getAllSpaceships(
            @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.findAllSpaceships(pageable));
    }

    @GetMapping("/{id}")
    @Tag(name = "Consultar nave espacial por Id", description = "Consulta por Id numérico")
    public ResponseEntity<SpaceshipResponseDTO> getSpaceshipById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findSpaceshipById(id));
    }

    @GetMapping("/by-name/{name-part}")
    @Tag(name = "Consultar naves espaciales por nombre", description = "Consulta por string que forma parte del nombre, con paginacion")
    public ResponseEntity<Page<SpaceshipResponseDTO>> getSpaceshipsByNamePart(
            @PathVariable(value = "name-part") String namePart, @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.findSpaceshipsByNamePart(namePart, pageable));
    }

    @PostMapping
    @Tag(name = "Guardar nave espacial", description = "Crea una nueva nave espacial en la base de datos (no se pueden repetir el Nombre y Programa de TV)")
    public ResponseEntity<SpaceshipResponseDTO> saveSpaceship(@RequestBody @Valid SpaceshipRequestDTO requestDTO) {
        return ResponseEntity.ok(service.saveSpaceship(requestDTO));
    }

    @PutMapping("/{id}")
    @Tag(name = "Actualizar nave espacial", description = "Actualiza validando que no exista otra nave con el mismo Nombre y Programa de TV")
    public ResponseEntity<SpaceshipResponseDTO> updateSpaceship(@PathVariable Long id,
                                                                @RequestBody @Valid SpaceshipRequestDTO requestDTO) {
        return ResponseEntity.ok(service.updateSpaceship(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Tag(name = "Eliminar nave espacial", description = "Elimina la nave espacial por Id, si existe en la base de datos")
    public ResponseEntity<Object> deleteSpaceship(@PathVariable Long id) {
        service.deleteSpaceshipById(id);
        return ResponseEntity.ok().build();
    }

}
