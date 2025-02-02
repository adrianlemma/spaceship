package com.mindata.w2m.spaceship.service.impl;

import com.mindata.w2m.spaceship.dto.SpaceshipRequestDTO;
import com.mindata.w2m.spaceship.dto.SpaceshipResponseDTO;
import com.mindata.w2m.spaceship.exception.SpaceshipNotFoundException;
import com.mindata.w2m.spaceship.mapper.SpaceshipMapper;
import com.mindata.w2m.spaceship.model.Spaceship;
import com.mindata.w2m.spaceship.repository.SpaceshipRepository;
import com.mindata.w2m.spaceship.service.SpaceshipService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.mindata.w2m.spaceship.constant.ErrorEnum.DUPLICATED_SPACESHIP;
import static com.mindata.w2m.spaceship.constant.ErrorEnum.NOT_FOUND_BY_ID;

@Service
public class SpaceshipServiceImpl implements SpaceshipService {

    private final SpaceshipRepository repository;
    private final SpaceshipMapper spaceshipMapper = SpaceshipMapper.INSTANCE;

    public SpaceshipServiceImpl(SpaceshipRepository repository, SpaceshipMapper spaceshipMapper) {
        this.repository = repository;
    }

    @Override
    public Page<SpaceshipResponseDTO> findAllSpaceships(Pageable pageable) {
        Page<Spaceship> spaceships = repository.findAll(pageable);
        return spaceships.map(spaceshipMapper::spaceshipToSpaceshipResponseDto);
    }

    @Override
    public SpaceshipResponseDTO findSpaceshipById(Long id) {
        Optional<Spaceship> spaceship = repository.findById(id);
        return spaceshipMapper.spaceshipToSpaceshipResponseDto(spaceship.orElseThrow(() ->
                new SpaceshipNotFoundException(NOT_FOUND_BY_ID.getCode(),
                        String.format(NOT_FOUND_BY_ID.getDescription(), id))));
    }

    @Override
    public Page<SpaceshipResponseDTO> findSpaceshipsByNamePart(String namePart, Pageable pageable) {
        Page<Spaceship> spaceships = repository.findBySpaceshipNameContainingIgnoreCase(namePart, pageable);
        return spaceships.map(spaceshipMapper::spaceshipToSpaceshipResponseDto);
    }

    @Override
    public SpaceshipResponseDTO saveSpaceship(SpaceshipRequestDTO requestDTO) {
        Spaceship spaceship = spaceshipMapper.spaceshipRequestDtoTOSpaceship(requestDTO);
        try {
            spaceship = repository.save(spaceship);
            return spaceshipMapper.spaceshipToSpaceshipResponseDto(spaceship);
        } catch (DataIntegrityViolationException ex) {
            throw new SpaceshipNotFoundException(DUPLICATED_SPACESHIP.getCode(),
                    String.format(DUPLICATED_SPACESHIP.getDescription(),
                            spaceship.getSpaceshipName(), spaceship.getTvProgram()));
        }
    }

    @Override
    public SpaceshipResponseDTO updateSpaceship(Long id, SpaceshipRequestDTO requestDTO) {
        Spaceship spaceship = repository.findById(id).orElseThrow(() ->
                new SpaceshipNotFoundException(NOT_FOUND_BY_ID.getCode(),
                        String.format(NOT_FOUND_BY_ID.getDescription(), id)));
        spaceship.setSpaceshipName(requestDTO.getSpaceshipName());
        spaceship.setTvProgram(requestDTO.getTvProgram());
        spaceship.setCapacity(requestDTO.getCapacity());
        try {
            spaceship = repository.save(spaceship);
            return spaceshipMapper.spaceshipToSpaceshipResponseDto(spaceship);
        } catch (DataIntegrityViolationException ex) {
            throw new SpaceshipNotFoundException(DUPLICATED_SPACESHIP.getCode(),
                    String.format(DUPLICATED_SPACESHIP.getDescription(),
                            spaceship.getSpaceshipName(), spaceship.getTvProgram()));
        }
    }

    @Override
    public void deleteSpaceshipById(Long id) {
        if (!repository.existsById(id))
            throw new SpaceshipNotFoundException(NOT_FOUND_BY_ID.getCode(),
                    String.format(NOT_FOUND_BY_ID.getDescription(), id));
        repository.deleteById(id);
    }
}
