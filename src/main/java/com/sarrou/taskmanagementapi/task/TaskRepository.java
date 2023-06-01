package com.sarrou.taskmanagementapi.task;

import com.sarrou.taskmanagementapi.task.service.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {

}
