package com.sarrou.taskmanagementapi.task.web;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarrou.api.TaskDto;
import com.sarrou.taskmanagementapi.task.service.Category;
import com.sarrou.taskmanagementapi.task.service.Task;
import com.sarrou.taskmanagementapi.task.service.TaskManager;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest
class TaskControllerTest {

    private static final String API_TASK_PATH = "/api/v1/task";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TaskManager taskManager;

    @MockBean
    private TaskConverter taskConverter;

    private Task expectedTask() {
        var category = new Category(1L, "socializing");

        return Task.builder()
                .taskId(1L).title("project 1").category(category)
                .description("backend rest api").dueDate(LocalDate.of(2023, 4, 22))
                .status(TaskDto.StatusEnum.OPEN).priority(TaskDto.PriorityEnum.HIGH).build();

    }

    @Test
    void getAllTasksReturnsHttpStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_TASK_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

//    @Test
//    void getAllTasksReturnsListOfTasks() throws Exception {
//        var category = new Category(1L, "socializing");
//
//        List<Task> taskEntityList = new ArrayList<>(
//                Arrays.asList(new Task(1L, "project 1", "backend rest api", category,
//                                LocalDate.of(2023, 4, 17),
//                                TaskDto.PriorityEnum.HIGH, TaskDto.StatusEnum.OPEN),
//                        new Task(2L, "project 2", "testing", category, LocalDate.of(2023, 4, 17),
//                                TaskDto.PriorityEnum.MEDIUM, TaskDto.StatusEnum.ONGOING)));
//        when(taskManager.getAllTasks()).thenReturn(taskEntityList);
//        mockMvc.perform(MockMvcRequestBuilders.get(API_TASK_PATH)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.size()").value(taskEntityList.size()))
//                .andDo(print());
//    }

    @Test
    void getTaskByIdReturnsTaskWithThatId() throws Exception {
        var category = new Category(1L, "socializing");
        Task taskEntity = new Task().builder().taskId(1L).title("project 1").category(category)
                .description("backend rest api").dueDate(LocalDate.of(2023, 4, 22))
                .status(TaskDto.StatusEnum.OPEN).priority(TaskDto.PriorityEnum.HIGH).build();
        when(taskManager.getTaskById(taskEntity.getTaskId())).thenReturn(taskEntity);
        when(taskConverter.mapToDto(any())).thenCallRealMethod();
        mockMvc.perform(MockMvcRequestBuilders.get(API_TASK_PATH + "/{taskId}", taskEntity.getTaskId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(taskEntity.getTaskId()))
                .andExpect(jsonPath("$.title").value(taskEntity.getTitle()))
                .andExpect(jsonPath("$.description").value(taskEntity.getDescription()))
                .andExpect(jsonPath("$.category_name").value(taskEntity.getCategory().getName()))
                .andExpect(jsonPath("$.dueDate").value(taskEntity.getDueDate().toString()))
                .andExpect(jsonPath("$.status").value(taskEntity.getStatus().getValue()))
                .andExpect(jsonPath("$.priority").value(taskEntity.getPriority().getValue()))
                .andDo(print());
    }

    @Test
    void getTaskByIdReturnsBadRequestWhenInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_TASK_PATH + "/{taskId}", "invalidId"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getTaskByIdReturnsNotFoundWhenIdIsNotPresent() throws Exception {
        when(taskManager.getTaskById(3L)).thenThrow(new EntityNotFoundException());
        when(taskConverter.mapToDto(any())).thenCallRealMethod();

        mockMvc.perform(MockMvcRequestBuilders.get(API_TASK_PATH + "/{taskId}", 3L))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void addTaskReturnsCreatedResponseStatus() throws Exception {
        var category = new Category(1L, "socializing");
        TaskDto task = new TaskDto().taskId(1L).title("project 1").categoryName(category.getName())
                .description("backend rest api").dueDate(LocalDate.of(2023, 4, 22))
                .status(TaskDto.StatusEnum.OPEN).priority(TaskDto.PriorityEnum.HIGH);
        when(taskManager.insertTask(task))
                .thenReturn(expectedTask());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        mockMvc.perform(post(API_TASK_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void addTaskReturnsBadRequestWhenTitleAndCategoryAreNull() throws Exception {
        TaskDto task = new TaskDto()
                .taskId(1L)
                //.title("project 1")
                // .categoryName("category name")
                .description("backend rest api")
                .dueDate(LocalDate.of(2023, 4, 22))
                .status(TaskDto.StatusEnum.OPEN).priority(TaskDto.PriorityEnum.HIGH);
        when(taskManager.insertTask(task))
                .thenReturn(expectedTask());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        mockMvc.perform(post(API_TASK_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

//    @Test
//    void addTaskReturnsBadRequestWhenTitleAndDescriptionAreEmptyOrLengthLessThanFiveChars() throws Exception {
//        Task task = new Task()
//                .taskId(1L)
//                .title("")
//                .description("")
//                .dueDate(LocalDate.of(2023, 4, 22))
//                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH);
//        when(taskManager.insertTask(task))
//                .thenReturn(expectedTask());
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
//        mockMvc.perform(post(API_TASK_PATH).contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(task)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }

    @Test
    void addTaskReturnsBadRequestWhenIllegalArgException() throws Exception {
        when(taskManager.insertTask(any())).thenThrow(new IllegalArgumentException());
        mockMvc.perform(post(API_TASK_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}