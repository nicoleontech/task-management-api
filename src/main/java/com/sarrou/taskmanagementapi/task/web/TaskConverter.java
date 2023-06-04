package com.sarrou.taskmanagementapi.task.web;

import com.sarrou.api.Task;
import com.sarrou.taskmanagementapi.task.CategoryRepository;
import com.sarrou.taskmanagementapi.task.service.TaskEntity;
import com.sarrou.taskmanagementapi.user.UserService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

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


    public Task mapToDto(TaskEntity taskEntity) {
        Task task = new Task();
        task.setTaskId(taskEntity.getTaskId());
        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());
        task.setCategoryName(taskEntity.getCategory().getName());
        task.setDueDate(taskEntity.getDueDate());
        task.setPriority(taskEntity.getPriority());
        task.setStatus(taskEntity.getStatus());
        return task;
    }

    public TaskEntity mapToEntity(Task task) {

        var email = userService.getLoggedInEmail();
        return TaskEntity.builder().
                taskId(task.getTaskId())
                .description(task.getDescription())
                .title(task.getTitle())
                .category(categoryRepository.findByName(task.getCategoryName()))
                .user(userService.findUserByEmail(email))
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .status(task.getStatus()).build();

    }

}
