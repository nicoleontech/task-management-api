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

    public List<CategoryEntity> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void initSetup(){
        CategoryEntity categoryEntity1 = new CategoryEntity(1l,"Socializing");
        CategoryEntity categoryEntity2 = new CategoryEntity(2l,"Hobbies");
        CategoryEntity categoryEntity3 = new CategoryEntity(3l,"Work");
        CategoryEntity categoryEntity4 = new CategoryEntity(4l,"Coding");
        categoryRepository.save(categoryEntity1);
        categoryRepository.save(categoryEntity2);
        categoryRepository.save(categoryEntity3);
        categoryRepository.save(categoryEntity4);
    }
}
