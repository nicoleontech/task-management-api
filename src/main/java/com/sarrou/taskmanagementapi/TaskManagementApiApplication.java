package com.sarrou.taskmanagementapi;

import com.sarrou.taskmanagementapi.task.service.CategoryService;
import com.sarrou.taskmanagementapi.task.service.TaskManager;
import com.sarrou.taskmanagementapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class TaskManagementApiApplication implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;

    private final TaskManager taskManager;

    @Autowired
    public TaskManagementApiApplication(UserService userService, CategoryService categoryService, TaskManager taskManager) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.taskManager = taskManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApiApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        userService.initSetup();
        categoryService.initSetup();
        taskManager.initSetup();
    }


}
