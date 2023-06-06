package com.sarrou.taskmanagementapi.task.web;

import com.sarrou.api.TaskDto;
import com.sarrou.taskmanagementapi.task.CategoryRepository;
import com.sarrou.taskmanagementapi.task.service.Task;
import com.sarrou.taskmanagementapi.user.UserService;
import org.springframework.stereotype.Component;

/*
Class that converts from entity to dto and vice versa.
 */
@Component
public class TaskConverter {

    private final CategoryRepository categoryRepository;

    private final UserService userService;


    public TaskConverter(CategoryRepository categoryRepository, UserService userService) {

        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }


    public TaskDto mapToDto(Task taskEntity) {
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(taskEntity.getTaskId());
        taskDto.setTitle(taskEntity.getTitle());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setCategoryName(taskEntity.getCategory().getName());
        taskDto.setDueDate(taskEntity.getDueDate());
        taskDto.setPriority(taskEntity.getPriority());
        taskDto.setStatus(taskEntity.getStatus());
        return taskDto;
    }

    public Task mapToEntity(TaskDto taskDto) {

        var email = userService.getLoggedInEmail();
        return Task.builder().
                taskId(taskDto.getTaskId())
                .description(taskDto.getDescription())
                .title(taskDto.getTitle())
                .category(categoryRepository.findByName(taskDto.getCategoryName()))
                .user(userService.findUserByEmail(email))
                .dueDate(taskDto.getDueDate())
                .priority(taskDto.getPriority())
                .status(taskDto.getStatus()).build();

    }

}
