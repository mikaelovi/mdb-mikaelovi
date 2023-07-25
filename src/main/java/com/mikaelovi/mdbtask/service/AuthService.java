package com.mikaelovi.mdbtask.service;

import com.mikaelovi.mdbtask.common.auth.JwtHelper;
import com.mikaelovi.mdbtask.common.enumeraion.Role;
import com.mikaelovi.mdbtask.model.AuthRequest;
import com.mikaelovi.mdbtask.model.AuthResponse;
import com.mikaelovi.mdbtask.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

        if (!this.username.equals(authRequest.username()) || !this.password.equals(authRequest.password()))
            throw new IllegalArgumentException("Invalid Credentials");

        var user = new User(authRequest.username(), authRequest.password(), Role.ADMIN);

        var jwt = jwtHelper.generateTokenForUser(user);

        return new AuthResponse(jwt);
    }
}
