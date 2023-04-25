package com.sarrou.taskmanagementapi.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarrou.api.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TASKS")
public class TaskEntity {

    @Id
    @Column(name = "TASKID")
    @GeneratedValue
    private Long taskId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DUE_DATE")
//    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY")
    private Task.PriorityEnum priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Task.StatusEnum status;


}
