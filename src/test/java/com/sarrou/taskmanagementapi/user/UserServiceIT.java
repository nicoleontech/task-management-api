package com.sarrou.taskmanagementapi.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceIT {

    @Autowired
    UserService sut;

    @Autowired
    UserRepository userRepository;

    @Test
    void hasUserWithEmail_returnsTrue_whenUserExists() {
        User user = new User("example@example.com");
        userRepository.save(user);
        var result = sut.hasUserWithEmail("example@example.com");
        assertThat(result).isTrue();
    }

    @Test
    void hasUserWithEmail_returnsFalse_whenUserNotExists(){
        var result = sut.hasUserWithEmail("example@example.com");
        assertThat(result).isFalse();
    }
}
