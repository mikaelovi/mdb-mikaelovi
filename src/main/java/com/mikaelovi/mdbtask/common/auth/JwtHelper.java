package com.mikaelovi.mdbtask.common.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtHelper {
    String extractUserNameFromToken(String token);

    String generateTokenForUser(UserDetails userDetails);

    boolean authenticateByToken(String token, UserDetails userDetails);
}
