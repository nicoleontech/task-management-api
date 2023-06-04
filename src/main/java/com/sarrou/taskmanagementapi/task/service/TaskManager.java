package com.sarrou.taskmanagementapi.task.service;

import com.sarrou.api.Task;
import com.sarrou.taskmanagementapi.task.web.TaskConverter;
import com.sarrou.taskmanagementapi.task.TaskRepository;
import com.sarrou.taskmanagementapi.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskManager {

    private final TaskRepository taskRepository;

    private final TaskConverter taskConverter;

    private final UserService userService;

    @Autowired
    public TaskManager(TaskRepository taskRepository, TaskConverter taskConverter, UserService userService) {
        this.taskRepository = taskRepository;
        this.taskConverter = taskConverter;
        this.userService = userService;
    }

    public TaskEntity getTaskById(Long id)  {

        Optional<TaskEntity> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return task.get();
    }

    public List<TaskEntity> getAllTasks() {

        return taskRepository.findAll();
    }

    public List<TaskEntity> getAllTasksForLoggedInUser() {
        String loggedInUserEmail =userService.getLoggedInEmail();

        return taskRepository.findAllByUserEmail(loggedInUserEmail);
    }


    @Transactional
    public TaskEntity insertTask(Task task) {
        TaskEntity taskEntity = taskConverter.mapToEntity(task);
        return taskRepository.save(taskEntity);
    }

    public TaskEntity updateTask(Task task) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(task.getTaskId());
        if (taskEntity.get() == null) {
            throw new RuntimeException();
        }
        return taskRepository.save(taskConverter.mapToEntity(task));
    }

    public void deleteTask(Long taskId, String apiKey) {
        taskRepository.deleteById(taskId);
    }


}
