package com.map_properties.spring_server.dto;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorValidationDTO extends ErrorMessageDTO {
    private Object errors;

    public ErrorValidationDTO(Map<String, Object> errors) {
        super("Validation failed!", HttpStatus.UNPROCESSABLE_ENTITY.value());
        this.errors = errors;
    }
}
