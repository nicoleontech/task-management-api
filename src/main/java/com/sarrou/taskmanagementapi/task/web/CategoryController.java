package com.sarrou.taskmanagementapi.task.web;

import com.sarrou.api.CategoryApi;
import com.sarrou.taskmanagementapi.task.service.Category;
import com.sarrou.taskmanagementapi.task.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
//TODO : remove from prod, only for dev purposes
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<List<String>> getAllCategoriesNames() {
        var categories = categoryService.getAllCategories()
                .stream().map(Category::getName).collect(Collectors.toList());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
