package com.sarrou.taskmanagementapi.task;

import com.sarrou.taskmanagementapi.task.service.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

   List<Task>  findAllByUserEmail(String email);
}
