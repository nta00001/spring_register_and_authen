package com.map_properties.spring_server.response;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorValidationResponse extends ErrorMessage {
    private Object errors;

    public ErrorValidationResponse(Map<String, Object> errors) {
        super("Validation failed!", HttpStatus.UNPROCESSABLE_ENTITY.value());
        this.errors = errors;
    }
}
