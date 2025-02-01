package com.map_properties.spring_server.config.role;

import java.lang.annotation.Target;

import com.map_properties.spring_server.enums.ERole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRoles {
    ERole[] value();
}
