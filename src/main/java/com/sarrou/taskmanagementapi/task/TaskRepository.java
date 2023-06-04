package com.sarrou.taskmanagementapi.task;

import com.sarrou.taskmanagementapi.task.service.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {

   List<TaskEntity>  findAllByUserEmail(String email);
}
