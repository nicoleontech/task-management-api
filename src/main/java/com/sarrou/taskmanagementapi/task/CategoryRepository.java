package com.sarrou.taskmanagementapi.task;

import com.sarrou.taskmanagementapi.task.service.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {


Category findByName(String name);

}
