package com.sarrou.taskmanagementapi.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService sut;

    @Test
    void hasUserWithEmail_isCalledCorrectly(){
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        sut.hasUserWithEmail("an email");
        verify(userRepository, times(1)).existsByEmail(argument.capture());
        assertThat(argument.getValue()).isEqualTo("an email");
    }


}