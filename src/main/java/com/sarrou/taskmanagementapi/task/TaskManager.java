package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskManager {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskEntity getTaskById(Long id) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new RuntimeException();
        }
        return task.get();
    }

    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity insertTask(Task task) {
        return taskRepository.save(convertToTaskEntity(task));
    }

    private static TaskEntity convertToTaskEntity(Task dto) {
        return new TaskEntity(
                dto.getTaskId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getDueDate(),
                dto.getPriority(),
                dto.getStatus());
    }
}
