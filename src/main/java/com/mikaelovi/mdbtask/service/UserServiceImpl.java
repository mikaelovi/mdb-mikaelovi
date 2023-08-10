package com.mikaelovi.mdbtask.service;


import com.mikaelovi.mdbtask.common.enumeraion.Role;
import com.mikaelovi.mdbtask.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

   @Value("${api.auth.username}")
    private String configUsername;

    @Value("${api.auth.password}")
    private String configPassword;

    public UserDetailsService userDetailsService() {
        return username -> {
            if (!configUsername.equals(username)) throw new UsernameNotFoundException("User not found");

            return new User(username, configPassword, Role.ADMIN);
        };
    }
}
