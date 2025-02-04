package com.mindata.w2m.spaceship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.w2m.spaceship.configuration.SecurityConfiguration;
import com.mindata.w2m.spaceship.dto.SpaceshipRequestDTO;
import com.mindata.w2m.spaceship.dto.SpaceshipResponseDTO;
import com.mindata.w2m.spaceship.mq.ErrorLogProducer;
import com.mindata.w2m.spaceship.service.SpaceshipService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.util.List;

import static com.mindata.w2m.spaceship.mock.MockData.mockSpaceshipRequest;
import static com.mindata.w2m.spaceship.mock.MockData.mockSpaceshipResponse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import(SecurityConfiguration.class)
class SpaceshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpaceshipService service;

    @MockitoBean
    private ErrorLogProducer logProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllSpaceships() throws Exception {
        List<SpaceshipResponseDTO> spaceships = Lists.list(mockSpaceshipResponse());
        Pageable pageable = PageRequest.of(0, 1);
        Page<SpaceshipResponseDTO> spaceshipResponse = new PageImpl<>(spaceships, pageable, spaceships.size());
        when(service.findAllSpaceships(any(Pageable.class))).thenReturn(spaceshipResponse);

        mockMvc.perform(get("/spaceships")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
        verifyNoInteractions(logProducer);
    }

    @Test
    void getSpaceshipById() throws Exception {
        when(service.findSpaceshipById(1L)).thenReturn(mockSpaceshipResponse());

        mockMvc.perform(get("/spaceships/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spaceship_name").value("spaceship"));
    }

    @Test
    void getSpaceshipsByNamePart() throws Exception {
        List<SpaceshipResponseDTO> spaceships = Lists.list(mockSpaceshipResponse());
        Pageable pageable = PageRequest.of(0, 1);
        Page<SpaceshipResponseDTO> spaceshipResponse = new PageImpl<>(spaceships, pageable, spaceships.size());
        when(service.findSpaceshipsByNamePart(eq("space"), any(Pageable.class))).thenReturn(spaceshipResponse);

        mockMvc.perform(get("/spaceships/by-name/{name-part}", "space")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"TEST_USER"})
    void saveSpaceship() throws Exception {
        when(service.saveSpaceship(any(SpaceshipRequestDTO.class))).thenReturn(mockSpaceshipResponse());

        mockMvc.perform(post("/spaceships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSpaceshipRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"TEST_USER"})
    void updateSpaceship() throws Exception {
        when(service.updateSpaceship(eq(1L), any(SpaceshipRequestDTO.class))).thenReturn(mockSpaceshipResponse());

        mockMvc.perform(put("/spaceships/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSpaceshipRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "test_admin", roles = {"TEST_ADMIN"})
    void deleteSpaceship() throws Exception {
        mockMvc.perform(delete("/spaceships/1"))
                .andExpect(status().isOk());
    }

}