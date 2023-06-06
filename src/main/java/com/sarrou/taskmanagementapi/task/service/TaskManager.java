package com.sarrou.taskmanagementapi.task.service;

import com.sarrou.api.TaskDto;
import com.sarrou.taskmanagementapi.task.Task;
import com.sarrou.taskmanagementapi.task.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskManager {

    private final TaskRepository taskRepository;

    private final com.sarrou.taskmanagementapi.task.TaskConverter taskConverter;

    @Autowired
    public TaskManager(TaskRepository taskRepository, com.sarrou.taskmanagementapi.task.TaskConverter taskConverter) {
        this.taskRepository = taskRepository;
        this.taskConverter = taskConverter;
    }

    public Task getTaskById(Long id)  {

        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return task.get();
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task insertTask(TaskDto task) {

        return taskRepository.save(taskConverter.mapToEntity(task));
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


}
