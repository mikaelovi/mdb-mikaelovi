package com.mikaelovi.mdbtask.service;

import com.mikaelovi.mdbtask.common.auth.JwtHelper;
import com.mikaelovi.mdbtask.common.enumeraion.Role;
import com.mikaelovi.mdbtask.model.AuthRequest;
import com.mikaelovi.mdbtask.model.AuthResponse;
import com.mikaelovi.mdbtask.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Value("${api.auth.username}")
    private String username;

    @Value("${api.auth.password}")
    private String password;


    public AuthService(AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        // match username
        if (!this.username.equals(authRequest.username()))
            throw new IllegalArgumentException("Invalid Credentials");

        // match password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

        var user = new User(authRequest.username(), password, Role.ADMIN);

        var jwt = jwtHelper.generateTokenForUser(user);

        return new AuthResponse(jwt, jwtHelper.getTokenExpiration(jwt));
    }
}
