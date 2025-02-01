package com.mindata.w2m.spaceship.mapper;

import com.mindata.w2m.spaceship.dto.SpaceshipRequestDTO;
import com.mindata.w2m.spaceship.dto.SpaceshipResponseDTO;
import com.mindata.w2m.spaceship.model.Spaceship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SpaceshipMapper {

    SpaceshipMapper INSTANCE = Mappers.getMapper(SpaceshipMapper.class);

    Spaceship spaceshipRequestDtoTOSpaceship(SpaceshipRequestDTO spaceshipRequestDTO);

    Spaceship spaceshipResponseDtoTOSpaceship(SpaceshipResponseDTO spaceshipResponseDTO);

    SpaceshipRequestDTO spaceshipToSpaceshipRequestDto(Spaceship spaceship);

    SpaceshipResponseDTO spaceshipToSpaceshipResponseDto(Spaceship spaceship);

}
