package com.sarrou.taskmanagementapi.task;

import com.sarrou.taskmanagementapi.task.service.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {


CategoryEntity findByName(String name);

}
