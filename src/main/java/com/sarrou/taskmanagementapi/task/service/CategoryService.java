package com.sarrou.taskmanagementapi.task.service;

import com.sarrou.taskmanagementapi.task.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void initSetup(){
        Category categoryEntity1 = new Category(1l,"Socializing");
        Category categoryEntity2 = new Category(2l,"Hobbies");
        Category categoryEntity3 = new Category(3l,"Work");
        Category categoryEntity4 = new Category(4l,"Coding");
        categoryRepository.save(categoryEntity1);
        categoryRepository.save(categoryEntity2);
        categoryRepository.save(categoryEntity3);
        categoryRepository.save(categoryEntity4);
    }
}
