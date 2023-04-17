package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TaskManager taskManager;

    @Test
    void getAllTasksReturnsListOfTasks() throws Exception {
        List<TaskEntity> taskEntityList = new ArrayList<>();
        taskEntityList.add(new TaskEntity(1L, "project 1", "backend rest api", LocalDate.of(2023, 4, 17),
                Task.PriorityEnum.HIGH, Task.StatusEnum.OPEN));
        taskEntityList.add(new TaskEntity(2L, "project 2", "testing", LocalDate.of(2023, 4, 17),
                Task.PriorityEnum.MEDIUM, Task.StatusEnum.OPEN));
        var resultList = taskManager.getAllTasks();
        when(taskManager.getAllTasks()).thenReturn(resultList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect( MockMvcResultMatchers.status().is(200));
    }

}