package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import com.sarrou.api.TaskApi;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TaskController implements TaskApi {

    private final TaskManager taskManager;
    private final TaskConverter taskConverter;

    @Autowired
    public TaskController(TaskManager taskManager, TaskConverter taskConverter) {
        this.taskManager = taskManager;
        this.taskConverter = taskConverter;
    }

    @Override
    public ResponseEntity<List<Task>> getAllTasks() {
//        List<TaskEntity> taskEntityList;
//        try {
//            taskEntityList = taskManager.getAllTasks();
//            List<Task> taskList = new ArrayList<>();
//            for (TaskEntity taskEntity : taskEntityList) {
//                Task task = taskConverter.mapToDto(taskEntity);
//                taskList.add(task);
//            }
//            return new ResponseEntity<>(taskList, HttpStatus.OK);
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        var taskList = taskManager.getAllTasks().stream()
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
