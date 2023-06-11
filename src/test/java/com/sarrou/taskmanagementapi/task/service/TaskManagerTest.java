package com.sarrou.taskmanagementapi.task.service;

import com.sarrou.taskmanagementapi.task.TaskRepository;
import com.sarrou.taskmanagementapi.task.web.TaskConverter;
import com.sarrou.taskmanagementapi.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskManagerTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskConverter taskConverter;
    @Mock
    UserService userService;
    @Mock
    CategoryService categoryService;

    @InjectMocks
    TaskManager sut;

    @Test
    void initSetup_savesTasks() {
        sut.initSetup();
        verify(userService, atLeast(1))
                .findUserByEmail("example@example.com");
        verify(taskRepository, atLeast(1))
                .saveAll(any());
    }
    
}