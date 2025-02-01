package com.map_properties.spring_server.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessageDTO {
    private String message;
    private Integer status;

    public ErrorMessageDTO(String message) {
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ErrorMessageDTO(String message, Integer status) {
        this.message = message;
        this.status = status;
    }
}
