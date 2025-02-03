package com.mindata.w2m.spaceship.service.impl;

import com.mindata.w2m.spaceship.dto.SpaceshipResponseDTO;
import com.mindata.w2m.spaceship.exception.SpaceshipDuplicatedException;
import com.mindata.w2m.spaceship.exception.SpaceshipNotFoundException;
import com.mindata.w2m.spaceship.model.Spaceship;
import com.mindata.w2m.spaceship.repository.SpaceshipRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.mindata.w2m.spaceship.constant.ErrorEnum.DUPLICATED_SPACESHIP;
import static com.mindata.w2m.spaceship.constant.ErrorEnum.NOT_FOUND_BY_ID;
import static com.mindata.w2m.spaceship.mock.MockData.mockSpaceship;
import static com.mindata.w2m.spaceship.mock.MockData.mockSpaceshipRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SpaceshipServiceImplTest {

    @Mock
    private SpaceshipRepository repository;

    @InjectMocks
    private SpaceshipServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllSpaceshipsOK() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Spaceship> spaceships = Lists.list(mockSpaceship());
        Page<Spaceship> spaceshipPage = new PageImpl<>(spaceships, pageable, spaceships.size());
        when(repository.findAll(pageable)).thenReturn(spaceshipPage);

        Page<SpaceshipResponseDTO> result = service.findAllSpaceships(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("spaceship", result.getContent().getFirst().getSpaceshipName());
    }

    @Test
    void findSpaceshipByIdOK() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockSpaceship()));
        SpaceshipResponseDTO result = service.findSpaceshipById(1L);
        assertEquals("spaceship", result.getSpaceshipName());
    }

    @Test
    void findSpaceshipByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        SpaceshipNotFoundException ex = assertThrows(SpaceshipNotFoundException.class, () ->
                service.findSpaceshipById(1L));
        assertEquals(NOT_FOUND_BY_ID.getCode(), ex.getCode());
    }

    @Test
    void findSpaceshipsByNamePartOK() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Spaceship> spaceships = Lists.list(mockSpaceship());
        Page<Spaceship> spaceshipPage = new PageImpl<>(spaceships, pageable, spaceships.size());
        when(repository.findBySpaceshipNameContainingIgnoreCase(anyString(), eq(pageable))).thenReturn(spaceshipPage);

        Page<SpaceshipResponseDTO> result = service.findSpaceshipsByNamePart("space", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("spaceship", result.getContent().getFirst().getSpaceshipName());
    }

    @Test
    void saveSpaceshipOK() {
        when(repository.save(any(Spaceship.class))).thenReturn(mockSpaceship());
        SpaceshipResponseDTO result = service.saveSpaceship(mockSpaceshipRequest());
        assertEquals(1L, result.getId());
    }

    @Test
    void saveSpaceshipDuplicated() {
        when(repository.save(any(Spaceship.class))).thenThrow(DataIntegrityViolationException.class);
        SpaceshipDuplicatedException ex = assertThrows(SpaceshipDuplicatedException.class, () ->
                service.saveSpaceship(mockSpaceshipRequest()));
        assertEquals(DUPLICATED_SPACESHIP.getCode(), ex.getCode());
    }

    @Test
    void updateSpaceshipOK() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockSpaceship()));
        when(repository.save(any(Spaceship.class))).thenReturn(mockSpaceship());
        SpaceshipResponseDTO result = service.updateSpaceship(1L, mockSpaceshipRequest());
        assertEquals(1L, result.getId());
    }

    @Test
    void updateSpaceshipNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        SpaceshipNotFoundException ex = assertThrows(SpaceshipNotFoundException.class, () ->
                service.updateSpaceship(1L, mockSpaceshipRequest()));
        assertEquals(NOT_FOUND_BY_ID.getCode(), ex.getCode());
        verify(repository, times(0)).save(any(Spaceship.class));
    }

    @Test
    void updateSpaceshipDuplicated() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockSpaceship()));
        when(repository.save(any(Spaceship.class))).thenThrow(DataIntegrityViolationException.class);
        SpaceshipNotFoundException ex = assertThrows(SpaceshipNotFoundException.class, () ->
                service.updateSpaceship(1L, mockSpaceshipRequest()));
        assertEquals(DUPLICATED_SPACESHIP.getCode(), ex.getCode());
    }

    @Test
    void deleteSpaceshipByIdOK() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deleteSpaceshipById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteSpaceshipByIdNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        SpaceshipNotFoundException ex = assertThrows(SpaceshipNotFoundException.class, () ->
                service.deleteSpaceshipById(1L));
        assertEquals(NOT_FOUND_BY_ID.getCode(), ex.getCode());
        verify(repository, times(0)).deleteById(1L);
    }

}