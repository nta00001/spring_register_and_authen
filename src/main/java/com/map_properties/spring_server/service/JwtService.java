package com.map_properties.spring_server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.repository.UserRepository;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class JwtService {
    // Replace this with a secure key in a real application, ideally fetched from
    // environment variables

    @Value("${security.jwt.secret}")
    private String SECRET;

    @Value("${security.jwt.expiration}")
    private Long expiredIn;

    @Autowired
    UserRepository userRepository;

    // Generate token with given
    @Transactional
    public Map<String, Object> generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        User user = userRepository.findByEmail(email);
        if (user.getRoles() != null) {
            claims.put("roles", user.getRoles().stream().map((role -> role.getCode())).collect(Collectors.toList()));
        }
        return createToken(claims, email);
    }

    // Create a JWT token with specified claims and subject
    private Map<String, Object> createToken(Map<String, Object> claims, String email) {
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredIn)) // Token valid for 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        Map<String, Object> response = new HashMap<>();
        response.put("access_token", token);
        response.put("expires_in", expiredIn / 1000);
        return response;
    }

    // Get the signing key for JWT token
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract the email from the token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract the expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObject = claims.get("roles");

        if (rolesObject instanceof List<?>) {
            List<?> rolesList = (List<?>) rolesObject;
            return rolesList.stream()
                    .filter(role -> role instanceof String) // Ensure type safety
                    .map(role -> (String) role) // Cast to String
                    .collect(Collectors.toList());
        }

        return new ArrayList<>(); // Return an empty list if roles are not found
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token against user details and expiration
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
