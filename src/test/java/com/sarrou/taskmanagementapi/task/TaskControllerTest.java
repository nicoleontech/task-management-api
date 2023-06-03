package com.sarrou.taskmanagementapi.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarrou.api.Task;
import com.sarrou.taskmanagementapi.task.service.CategoryEntity;
import com.sarrou.taskmanagementapi.task.service.CategoryService;
import com.sarrou.taskmanagementapi.task.service.TaskEntity;
import com.sarrou.taskmanagementapi.task.service.TaskManager;
import com.sarrou.taskmanagementapi.task.web.TaskController;
import com.sarrou.taskmanagementapi.task.web.TaskConverter;
import com.sarrou.taskmanagementapi.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
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


@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
class TaskControllerTest {

    private static final String API_TASK_PATH = "/api/v1/task";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TaskManager taskManager;

    @MockBean
    private TaskConverter taskConverter;

    @MockBean
    private UserService userService;

    @MockBean
    private CategoryService categoryService;

    @Mock
    private Authentication authentication;


    @BeforeEach
    void setUp() {
        when(authentication.getPrincipal()).thenReturn(createJwtToken());

        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
    }

    private Jwt createJwtToken() {
        return Jwt.withTokenValue("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJtQ3B0XzJnakhleW03cGMxdGZObFVxTkEtZ0o3UUs4NTNHWlFXRUJxSDQwIn0.eyJleHAiOjE2ODUyMTkyMDIsImlhdCI6MTY4NTIxODkwMiwiYXV0aF90aW1lIjoxNjg1MjE4MzU5LCJqdGkiOiJhYzM1ZDRhNC02MWY0LTRhNjItYWY2MS1iODUxOTM1N2NlOWMiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODgvYXV0aC9yZWFsbXMvVGFzay1tYW5hZ2VtZW50IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImJiNjE0MWI1LTBhMGYtNGI2OS05OTU0LWQ2MDZjN2IyMDhlMyIsInR5cCI6IkJlYXJlciIsImF6cCI6InRhc2stbWFuYWdlbWVudC1zcGEiLCJub25jZSI6ImZkZTVjMmNmLTIwZTAtNGNkYi04NGI0LTQzY2RiZTVlODQ5NiIsInNlc3Npb25fc3RhdGUiOiJiOGZmZGVkNi1mMzliLTQ4ODgtOTgzYS1hZWMzNDEwMzg2NmEiLCJhY3IiOiIwIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NDIwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy10YXNrLW1hbmFnZW1lbnQiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsInNpZCI6ImI4ZmZkZWQ2LWYzOWItNDg4OC05ODNhLWFlYzM0MTAzODY2YSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJuaWNvbGVzYXIiLCJnaXZlbl9uYW1lIjoiIiwiZmFtaWx5X25hbWUiOiIiLCJlbWFpbCI6Im5pY29sZS5zYXIuOTNAZ21haWwuY29tIn0.cU5yqeC8O9MawfrSeI1B1L1axPyCmpABFUDhP64s23O6xo9Cu_nSwRImwmam3swjYe-Gwl4ootFSNTQjQJAVJDpDrHYAbLuB8rGIB7VnZVvzZyG7EGauUBapgE2myVk-MRmY45HrprWGd5Z7HsJAm_ro1p8_YeERwCp1ZAUZnGIKiHNH3MxHE4NDcu_WVk2Iaom_1bXjrmXhTUgaXewuAfEQU4Rgozx_Wpqodc6jMC5ukiiN1p5JrR5-V90Gemde6GdNi-Lb4KAOYgLrEX6DOg9ehmazY6nw6C8yhTC2BXZzERcxklCWMET_T2-uq-HT2NgNzoRkwxUQl03_KhfjBw")
                .header("alj", "none")
                .claim("azp", "task-management-spa")
                .claim("email", "example@example.com")
                .build();
    }


    private TaskEntity expectedTask() {
        var category = new CategoryEntity(1L, "socializing");

        return TaskEntity.builder()
                .taskId(1L).title("project 1").category(category)
                .description("backend rest api").dueDate(LocalDate.of(2023, 4, 22))
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH).build();

    }

    @Test
    void getAllTasksReturnsHttpStatusOk() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_TASK_PATH)
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, createJwtToken().getTokenValue())
        ).andExpect(MockMvcResultMatchers.status().is(200));


    }

    @Test
    void getAllTasksReturnsListOfTasks() throws Exception {
        var category = new CategoryEntity(1L, "socializing");

        List<TaskEntity> taskEntityList = new ArrayList<>(
                Arrays.asList(new TaskEntity(1L, "project 1", "backend rest api", category,
                                LocalDate.of(2023, 4, 17),
                                Task.PriorityEnum.fromValue("high"), Task.StatusEnum.OPEN),
                        new TaskEntity(2L, "project 2", "testing", category, LocalDate.of(2023, 4, 17),
                                Task.PriorityEnum.MEDIUM, Task.StatusEnum.ONGOING)));
        when(taskManager.getAllTasks()).thenReturn(taskEntityList);
        mockMvc.perform(MockMvcRequestBuilders.get(API_TASK_PATH)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(taskEntityList.size()))
                .andDo(print());
    }

    @Test
    void getTaskByIdReturnsTaskWithThatId() throws Exception {
        var category = new CategoryEntity(1L, "socializing");
        TaskEntity taskEntity = new TaskEntity().builder().taskId(1L).title("project 1").category(category)
                .description("backend rest api").dueDate(LocalDate.of(2023, 4, 22))
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH).build();
        when(taskManager.getTaskById(taskEntity.getTaskId())).thenReturn(taskEntity);
        when(taskConverter.mapToDto(any())).thenCallRealMethod();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_TASK_PATH + "/{taskId}", taskEntity.getTaskId())
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(taskEntity.getTaskId()))
                .andExpect(jsonPath("$.title").value(taskEntity.getTitle()))
                .andExpect(jsonPath("$.description").value(taskEntity.getDescription()))
                .andExpect(jsonPath("$.categoryName").value(taskEntity.getCategory().getName()))
                .andExpect(jsonPath("$.dueDate").value(taskEntity.getDueDate().toString()))
                .andExpect(jsonPath("$.status").value(taskEntity.getStatus().getValue()))
                .andExpect(jsonPath("$.priority").value(taskEntity.getPriority().getValue()))
                .andDo(print());
    }

    @Test
    void getTaskByIdReturnsBadRequestWhenInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_TASK_PATH + "/{taskId}", "invalidId")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getTaskByIdReturnsNotFoundWhenIdIsNotPresent() throws Exception {
        when(taskManager.getTaskById(3L)).thenThrow(new EntityNotFoundException());
        when(taskConverter.mapToDto(any())).thenCallRealMethod();

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_TASK_PATH + "/{taskId}", 3L)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void addTaskReturnsCreatedResponseStatus() throws Exception {
        Task task = new Task().taskId(1L).title("project 1").categoryName("socializing")
                .description("backend rest api").dueDate(LocalDate.of(2023, 4, 22))
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH);
        when(taskManager.insertTask(task))
                .thenReturn(expectedTask());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        mockMvc.perform(post(API_TASK_PATH)
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void addTaskReturnsBadRequestWhenTitleAndCategoryAreNull() throws Exception {
        Task task = new Task()
                .taskId(1L)
                //.title("project 1")
                // .categoryName("category name")
                .description("backend rest api")
                .dueDate(LocalDate.of(2023, 4, 22))
                .status(Task.StatusEnum.OPEN)
                .priority(Task.PriorityEnum.HIGH);
        when(taskManager.insertTask(task))
                .thenReturn(expectedTask());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        mockMvc.perform(post(API_TASK_PATH)
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(task))
                        .contentType(MediaType.APPLICATION_JSON))
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

//    @Test
//    void addTaskReturnsBadRequestWhenIllegalArgException() throws Exception {
//        when(taskManager.insertTask(any())).thenThrow(new IllegalArgumentException());
//        mockMvc.perform(post(API_TASK_PATH).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }


}