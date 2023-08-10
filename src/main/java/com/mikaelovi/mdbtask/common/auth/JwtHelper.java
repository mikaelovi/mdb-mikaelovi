package com.mikaelovi.mdbtask.common.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

public interface JwtHelper {
    String extractUserNameFromToken(String token);

    String generateTokenForUser(UserDetails userDetails);

    boolean authenticateByToken(String token, UserDetails userDetails);

    LocalDateTime getTokenExpiration(String token);
}
