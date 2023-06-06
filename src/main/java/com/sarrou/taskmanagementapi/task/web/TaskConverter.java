package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
Class that converts from entity to dto and vice versa.
 */
@Component
public class TaskConverter {
    private final CategoryRepository categoryRepository;

    @Autowired
    public TaskConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public TaskDto mapToDto(Task taskEntity) {
        TaskDto task = new TaskDto();
        task.setTaskId(taskEntity.getTaskId());
        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());
        task.setCategoryName(taskEntity.getCategory().getName());
        task.setDueDate(taskEntity.getDueDate());
        task.setPriority(taskEntity.getPriority());
        task.setStatus(taskEntity.getStatus());
        return task;
    }

    public Task mapToEntity(TaskDto task) {

        return Task.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .category(categoryRepository.findCategoryByName(task.getCategoryName()))
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .build();
    }

}
