package com.mindata.w2m.spaceship.controller;

import com.mindata.w2m.spaceship.dto.SpaceshipErrorDTO;
import com.mindata.w2m.spaceship.exception.SpaceshipDuplicatedException;
import com.mindata.w2m.spaceship.exception.SpaceshipNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashSet;
import java.util.Set;

import static com.mindata.w2m.spaceship.constant.ErrorEnum.*;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(SpaceshipNotFoundException.class)
    public ResponseEntity<SpaceshipErrorDTO> spaceshipNotFondExceptionHandler(SpaceshipNotFoundException ex) {
        SpaceshipErrorDTO errorDTO = new SpaceshipErrorDTO(ex.getCode(), ex.getDescription());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(SpaceshipDuplicatedException.class)
    public ResponseEntity<SpaceshipErrorDTO> spaceshipDuplicatedExceptionHandler(SpaceshipDuplicatedException ex) {
        SpaceshipErrorDTO errorDTO = new SpaceshipErrorDTO(ex.getCode(), ex.getDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SpaceshipErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Set<String> errorMessage = new HashSet<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessage.add(error.getDefaultMessage()));
        SpaceshipErrorDTO errorDTO = new SpaceshipErrorDTO(FIELD_VALIDATION.getCode(),
                String.format(FIELD_VALIDATION.getDescription(), errorMessage));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<SpaceshipErrorDTO> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        SpaceshipErrorDTO errorDTO = new SpaceshipErrorDTO(INVALID_VARIABLE.getCode(),
                String.format(INVALID_VARIABLE.getDescription(), ex.getName()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SpaceshipErrorDTO> genericExceptionHandler(Exception ex) {
        SpaceshipErrorDTO errorDTO = new SpaceshipErrorDTO(GENERIC_EXCEPTION.getCode(), GENERIC_EXCEPTION.getDescription());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

}
