package com.map_properties.spring_server.filter;

import com.map_properties.spring_server.service.JwtService;
import com.map_properties.spring_server.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.map_properties.spring_server.entity.enums.Role;

import com.map_properties.spring_server.response.ErrorMessage;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Lazy
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String authHeader = request.getHeader("Authorization");
            String token = null;
            String email = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                try {
                    email = jwtService.extractEmail(token);
                } catch (Exception e) {
                    String json = new Gson()
                            .toJson(new ErrorMessage("Invalid JWT token", HttpServletResponse.SC_UNAUTHORIZED));
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(json);
                    return;
                }
            }

            // middleware permission check

            // String path = request.getServletPath();
            // String method = request.getMethod();
            // List<String> roles = jwtService.extractRoles(token);
            // if (path.contains("/me") && method.equals("GET") &&
            // roles.contains(Role.ROLE_ADMIN.toString())) {
            // String json = new Gson().toJson(new ErrorMessage("No Permission",
            // HttpServletResponse.SC_FORBIDDEN));
            // response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            // response.getWriter().write(json);
            // return;
            // }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            String json = new Gson()
                    .toJson(new ErrorMessage("Authentication failed", HttpServletResponse.SC_UNAUTHORIZED));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(json);
        }
    }
}
