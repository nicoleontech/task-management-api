package com.sarrou.taskmanagementapi.task.service;

import com.sarrou.api.TaskDto;
import com.sarrou.taskmanagementapi.task.TaskRepository;
import com.sarrou.taskmanagementapi.task.web.TaskConverter;
import com.sarrou.taskmanagementapi.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Slf4j
public class TaskManager {

    private final TaskRepository taskRepository;

    private final TaskConverter taskConverter;

    private final UserService userService;

    private final CategoryService categoryService;

    @Autowired
    public TaskManager(TaskRepository taskRepository, TaskConverter taskConverter, UserService userService, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.taskConverter = taskConverter;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public Task getTaskById(Long id) {

        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return task.get();
    }

    public List<Task> getAllTasks() {

        return taskRepository.findAll();
    }

    public List<Task> getAllTasksForLoggedInUser() {
        String loggedInUserEmail = userService.getLoggedInEmail();

        return taskRepository.findAllByUserEmail(loggedInUserEmail);
    }


    @Transactional
    public Task insertTask(TaskDto task) {
        Task taskEntity = taskConverter.mapToEntity(task);
        return taskRepository.save(taskEntity);
    }

    public Task updateTask(TaskDto task) {
        Optional<Task> taskEntity = taskRepository.findById(task.getTaskId());
        if (taskEntity.get() == null) {
            throw new RuntimeException();
        }
        return taskRepository.save(taskConverter.mapToEntity(task));
    }

    public void deleteTask(Long taskId, String apiKey) {
        taskRepository.deleteById(taskId);
    }

    public void initSetup() {
        log.info("saving tasks");
        var firstTask = Task.builder().title("finish building api")
                .description("use Docker and write start up scripts")
                .category(categoryService.findByName("Coding"))
                .user(userService.findUserByEmail("example@example.com"))
                .dueDate(LocalDate.of(2023, 6, 21))
                .status(TaskDto.StatusEnum.OPEN)
                .priority(TaskDto.PriorityEnum.HIGH)
                .build();

        var secondTask = Task.builder().title("visit art exhibition")
                .description("art exhibition at National Gallery")
                .category(categoryService.findByName("Socializing"))
                .user(userService.findUserByEmail("example@example.com"))
                .dueDate(LocalDate.of(2023, 6, 23))
                .status(TaskDto.StatusEnum.OPEN)
                .priority(TaskDto.PriorityEnum.LOW)
                .build();
        taskRepository.saveAll(List.of(firstTask, secondTask));
    }


}
