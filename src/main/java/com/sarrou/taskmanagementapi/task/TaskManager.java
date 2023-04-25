package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskManager {

    private final TaskRepository taskRepository;

    private final TaskConverter taskConverter;

    @Autowired
    public TaskManager(TaskRepository taskRepository, TaskConverter taskConverter) {
        this.taskRepository = taskRepository;
        this.taskConverter = taskConverter;
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

    public TaskEntity insertTask(Task task) {
        return taskRepository.save(taskConverter.mapToEntity(task));
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
