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

    public void initSetup(){
        User user = new User("nicole.sar.93@gmail.com");
        user.setUsername("nicolesar");
        userRepository.save(user);
    }


}
