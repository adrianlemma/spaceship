package com.mindata.w2m.spaceship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.w2m.spaceship.configuration.SecurityConfiguration;
import com.mindata.w2m.spaceship.dto.SpaceshipRequestDTO;
import com.mindata.w2m.spaceship.exception.SpaceshipDuplicatedException;
import com.mindata.w2m.spaceship.exception.SpaceshipNotFoundException;
import com.mindata.w2m.spaceship.service.SpaceshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.mindata.w2m.spaceship.constant.ErrorEnum.*;
import static com.mindata.w2m.spaceship.constant.ErrorEnum.GENERIC_EXCEPTION;
import static com.mindata.w2m.spaceship.mock.MockData.mockSpaceshipRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfiguration.class)
class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpaceshipService service;

    @InjectMocks
    private SpaceshipController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void spaceshipNotFondExceptionHandler() throws Exception {
        when(service.findSpaceshipById(1L))
                .thenThrow(new SpaceshipNotFoundException(NOT_FOUND_BY_ID.getCode(), NOT_FOUND_BY_ID.getDescription()));

        mockMvc.perform(get("/spaceships/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error_code").value(NOT_FOUND_BY_ID.getCode()));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"TEST_USER"})
    void spaceshipDuplicatedExceptionHandler() throws Exception {
        when(service.saveSpaceship(any(SpaceshipRequestDTO.class)))
                .thenThrow(new SpaceshipDuplicatedException(DUPLICATED_SPACESHIP.getCode(), DUPLICATED_SPACESHIP.getDescription()));

        mockMvc.perform(post("/spaceships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSpaceshipRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error_code").value(DUPLICATED_SPACESHIP.getCode()));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"TEST_USER"})
    void handleValidationExceptions() throws Exception {
        SpaceshipRequestDTO illegalRequest = mockSpaceshipRequest();
        illegalRequest.setSpaceshipName(null);

        mockMvc.perform(post("/spaceships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(illegalRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error_code").value(FIELD_VALIDATION.getCode()));
    }

    @Test
    void methodArgumentTypeMismatchExceptionHandler() throws Exception {
        mockMvc.perform(get("/spaceships/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error_code").value(INVALID_VARIABLE.getCode()));
    }

    @Test
    @WithMockUser(username = "test_admin", roles = {"TEST_ADMIN"})
    void genericExceptionHandler() throws Exception {
        doThrow(RuntimeException.class).when(service).deleteSpaceshipById(eq(1L));
        mockMvc.perform(delete("/spaceships/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error_code").value(GENERIC_EXCEPTION.getCode()));
    }

}