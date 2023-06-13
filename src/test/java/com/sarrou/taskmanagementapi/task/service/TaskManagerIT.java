package com.sarrou.taskmanagementapi.task.service;

import com.sarrou.taskmanagementapi.task.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class TaskManagerIT {
    @Autowired
    TaskManager sut;

    @Autowired
    TaskRepository repository;

    @Test
    void initSetup_createsEntries() {
        sut.initSetup();

        var result = repository.findAll();
        Assertions.assertThat(result).isNotNull().isNotEmpty();
    }

}