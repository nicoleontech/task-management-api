package com.sarrou.taskmanagementapi.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public boolean hasUserWithEmail(String email){
        return userRepository.existsByEmail(email);
    }



}