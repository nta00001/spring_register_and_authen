package com.map_properties.spring_server.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    private String message;
    private Integer status;

    public ErrorMessage(String message) {
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ErrorMessage(String message, Integer status) {
        this.message = message;
        this.status = status;
    }
}
