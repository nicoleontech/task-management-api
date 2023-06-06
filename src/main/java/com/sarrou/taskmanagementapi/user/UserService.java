package com.sarrou.taskmanagementapi.user;

import com.sarrou.taskmanagementapi.user.User;
import com.sarrou.taskmanagementapi.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public boolean hasUserWithEmail(String email) {

        return userRepository.existsByEmail(email);
    }

    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findUserByEmail(String email) {
        var user = userRepository.findByEmail(email);
        return user;
    }

    public String getLoggedInEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = (Jwt) authentication.getCredentials();
            String email = (String) jwt.getClaims().get("email");
            return email;        }
        return null;
    }

    public void initSetup() {
        User user = new User("nicole.sar.93@gmail.com");
        user.setUsername("nicolesar");
        userRepository.save(user);

        User user1 = new User("example@example.com");
        user1.setUsername("example");
        userRepository.save(user1);
    }

    private User convertToEntity(User userdto) {
        User user = new User();
        user.setUsername(userdto.getUsername());
        user.setId(userdto.getId());
        user.setEmail(userdto.getEmail());
        return user;
    }

    private User convertToDto(com.sarrou.taskmanagementapi.user.User user) {
        User userDto = new User();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        return userDto;
    }


}
