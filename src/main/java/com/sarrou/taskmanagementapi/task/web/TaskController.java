package com.sarrou.taskmanagementapi.task.web;

import com.sarrou.api.TaskApi;
import com.sarrou.api.TaskDto;
import com.sarrou.taskmanagementapi.task.service.Task;
import com.sarrou.taskmanagementapi.task.service.TaskManager;
import com.sarrou.taskmanagementapi.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TaskDto>> getAllTasks() {
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
    public ResponseEntity<TaskDto> getTaskById(Long taskId, String apiKey) {
        Task taskEntity;
        try {
            taskEntity = taskManager.getTaskById(taskId);
            TaskDto task = taskConverter.mapToDto(taskEntity);
            // System.out.println(taskEntity.getTaskId());
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<TaskDto> addTask(TaskDto task) {
        Task taskEntity = taskManager.insertTask(task);
        TaskDto taskDto = taskConverter.mapToDto(taskEntity);
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }



    @Override
    public ResponseEntity<TaskDto> updateTask(TaskDto task) {
        Task taskEntity = taskManager.updateTask(task);
        TaskDto taskDto = taskConverter.mapToDto(taskEntity);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId, String apiKey) {
        taskManager.deleteTask(taskId, apiKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
