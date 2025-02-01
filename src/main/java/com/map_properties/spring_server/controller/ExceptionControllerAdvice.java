package com.map_properties.spring_server.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.map_properties.spring_server.dto.ErrorMessageDTO;
import com.map_properties.spring_server.dto.ErrorValidationDTO;
import com.map_properties.spring_server.exception.LoginException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler // when Invalid Credentials
    private ResponseEntity<ErrorMessageDTO> handleInvalidCredentialsException(
            BadCredentialsException ex) {
        return new ResponseEntity<ErrorMessageDTO>(
                new ErrorMessageDTO("Invalid credentials", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler // when login failed
    private ResponseEntity<ErrorMessageDTO> handleLoginException(
            LoginException ex) {
        return new ResponseEntity<ErrorMessageDTO>(
                new ErrorMessageDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler // Invalid validation requests
    private ResponseEntity<ErrorValidationDTO> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, Object> errors = new HashMap<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), new String[] { error.getDefaultMessage() });
        }

        ErrorValidationDTO response = new ErrorValidationDTO(errors);
        return new ResponseEntity<ErrorValidationDTO>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler // No Resource Found Exception handler
    private ResponseEntity<ErrorMessageDTO> handleNoResourceFoundException(
            NoResourceFoundException ex) {
        return new ResponseEntity<ErrorMessageDTO>(
                new ErrorMessageDTO(ex.getMessage(), ex.getStatusCode().value()), ex.getStatusCode());
    }

    @ExceptionHandler // other ExceptionHandler
    private ResponseEntity<ErrorMessageDTO> handleOthersException(
            Exception ex) {
        return new ResponseEntity<ErrorMessageDTO>(
                new ErrorMessageDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
