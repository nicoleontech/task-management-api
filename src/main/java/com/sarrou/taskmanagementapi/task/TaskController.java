package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import com.sarrou.api.TaskApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController implements TaskApi {

    private final TaskManager taskManager;

    @Autowired
    public TaskController(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public ResponseEntity<List<Task>> getAllTasks() {
        List<TaskEntity> taskEntityList;
        try {
            taskEntityList = taskManager.getAllTasks();
            List<Task> taskList = new ArrayList<>();
            for (TaskEntity taskEntity : taskEntityList) {
                Task task = new Task();
                task.setTaskId(taskEntity.getTaskId());
                task.setTitle(taskEntity.getTitle());
                task.setDescription(taskEntity.getDescription());
                task.setStatus(taskEntity.getStatus());
                task.setPriority(taskEntity.getPriority());
                task.setDueDate(taskEntity.getDueDate());
                taskList.add(task);
            }
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    @Override
    public ResponseEntity<Task> getTaskById(Long taskId, String apiKey) {
        TaskEntity taskEntity;
        try {
            taskEntity = taskManager.getTaskById(taskId);
            Task task = new Task();
            System.out.println(taskEntity.getTaskId());
            task.setTaskId(taskId);
            task.setTitle(taskEntity.getTitle());
            task.setDescription(taskEntity.getDescription());
            task.setDueDate(taskEntity.getDueDate());
            task.setStatus(taskEntity.getStatus());
            task.setPriority(taskEntity.getPriority());
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private Task map(TaskEntity taskEntity) {
        Task task = new Task();
        task.setTaskId(taskEntity.getTaskId());
        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());
        task.setDueDate(taskEntity.getDueDate());
        task.setPriority(taskEntity.getPriority());
        task.setStatus(taskEntity.getStatus());
        return task;
    }
}
