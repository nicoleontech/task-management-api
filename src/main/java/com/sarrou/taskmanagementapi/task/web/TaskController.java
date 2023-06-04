package com.sarrou.taskmanagementapi.task.web;

import com.sarrou.api.Task;
import com.sarrou.api.TaskApi;
import com.sarrou.taskmanagementapi.config.WebSecurityConfiguration;
import com.sarrou.taskmanagementapi.task.service.TaskEntity;
import com.sarrou.taskmanagementapi.task.service.TaskManager;
import com.sarrou.taskmanagementapi.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
//TODO : remove from prod, only for dev purposes
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController implements TaskApi {

    private final TaskManager taskManager;

    private final TaskConverter taskConverter;

    private final UserService userService;



    @Autowired
    public TaskController(TaskManager taskManager, TaskConverter taskConverter, UserService userService) {
        this.taskManager = taskManager;
        this.taskConverter = taskConverter;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<Task>> getAllTasks() {
//        var taskList = taskManager.getAllTasks().stream()
//                .map(taskConverter::mapToDto)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(taskList, HttpStatus.OK);
        var taskList = taskManager.getAllTasksForLoggedInUser().stream()
                .map(taskConverter::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> getTaskById(Long taskId, String apiKey) {
        TaskEntity taskEntity;
        try {
            taskEntity = taskManager.getTaskById(taskId);
            Task task = taskConverter.mapToDto(taskEntity);
            // System.out.println(taskEntity.getTaskId());
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<Task> addTask(Task task) {
        TaskEntity taskEntity = taskManager.insertTask(task);
        Task taskDto = taskConverter.mapToDto(taskEntity);
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Task> updateTask(Task task) {
        TaskEntity taskEntity = taskManager.updateTask(task);
        Task taskDto = taskConverter.mapToDto(taskEntity);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId, String apiKey) {
        taskManager.deleteTask(taskId, apiKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
