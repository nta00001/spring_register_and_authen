package com.map_properties.spring_server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CastUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convertToClass(Object object, Class<T> targetClass) {
        try {
            String json = objectMapper.writeValueAsString(object);
            System.out.println(json);
            return objectMapper.readValue(json, targetClass);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
