package com.map_properties.spring_server.config.role;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.gson.Gson;
import com.map_properties.spring_server.dto.ErrorMessageDTO;
import com.map_properties.spring_server.enums.ERole;
import com.map_properties.spring_server.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class RolesInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        if (!handlerMethod.hasMethodAnnotation(Roles.class)) {
            return true;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            String json = new Gson().toJson(new ErrorMessageDTO("No Permission",
                    HttpServletResponse.SC_FORBIDDEN));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(json);
            return false;
        }
        String token = authHeader.substring(7);
        List<String> claimRoles = jwtService.extractRoles(token);

        Roles permissions = handlerMethod.getMethodAnnotation(Roles.class);
        if (permissions == null) {
            return true;
        }
        ERole[] requiredRoles = permissions.value(); // Get the value of
                                                     // the Permissions
                                                     // annotation

        // Check if the user has any of the required roles
        List<String> requireStringdRoles = Arrays.stream(requiredRoles)
                .map(ERole::getCode)
                .collect(Collectors.toList());

        // Check if any of the user's roles match the required roles
        if (claimRoles.containsAll(requireStringdRoles)) {
            return true;
        }

        String json = new Gson().toJson(new ErrorMessageDTO("No Permission",
                HttpServletResponse.SC_FORBIDDEN));
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(json);
        return false; // User does not have permission
    }
}
