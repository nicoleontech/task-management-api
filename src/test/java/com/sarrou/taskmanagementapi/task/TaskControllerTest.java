package com.sarrou.taskmanagementapi.task;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(SpringExtension.class)
@WebMvcTest
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TaskManager taskManager;

    TaskEntity taskEntity1 = new TaskEntity(1L, "project 1", "backend rest api", LocalDate.of(2023, 4, 17),
            Task.PriorityEnum.HIGH, Task.StatusEnum.OPEN);

    TaskEntity taskEntity2 = new TaskEntity(1L, "project 2", "testing", LocalDate.of(2023, 4, 17),
            Task.PriorityEnum.MEDIUM, Task.StatusEnum.ONGOING);

    @Test
    void getAllTasksReturnsHttpStatusOk() throws Exception {
        List<TaskEntity> taskEntityList = new ArrayList<>();
        taskEntityList.add(taskEntity1);
        taskEntityList.add(taskEntity2);
        var resultList = taskManager.getAllTasks();
        when(taskManager.getAllTasks()).thenReturn(resultList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void addTaskReturnsCreatedResponse() throws Exception {
        Task task = new Task().taskId(1L).title("project 1")
                .description("backend rest api").dueDate(LocalDate.of(2023, 4, 22))
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH);
        when(taskManager.insertTask(task))
                .thenReturn(expectedTask());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        mockMvc.perform(post("/api/task").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().isCreated()
                );
    }

    private TaskEntity expectedTask() {
        return TaskEntity.builder()
                .taskId(1L).title("project 1")
                .description("backend rest api").dueDate(LocalDate.of(2023, 4, 22))
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH).build();

    }

}