package com.mikaelovi.mdbtask.common.auth;


import com.mikaelovi.mdbtask.common.enumeraion.Role;
import com.mikaelovi.mdbtask.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserAuthenticationHelper {

    @Value("${api.auth.username}")
    private String username;

    @Value("${api.auth.password}")
    private String password;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                if (!getUsername().equals(username)) throw new UsernameNotFoundException("User not found");

                return new User(username, password, Role.ADMIN);
            }
        };
    }
}
