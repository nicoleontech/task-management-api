package com.sarrou.taskmanagementapi;

import com.sarrou.taskmanagementapi.task.service.CategoryService;
import com.sarrou.taskmanagementapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TaskManagementApiApplication implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public TaskManagementApiApplication(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userService.initSetup();
        categoryService.initSetup();
    }


}
