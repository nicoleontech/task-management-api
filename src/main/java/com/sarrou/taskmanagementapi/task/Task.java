package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.TaskDto;
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
public class Task {

    @Id
    @Column(name = "TASKID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "DUE_DATE")
//    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY")
    private TaskDto.PriorityEnum priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private TaskDto.StatusEnum status;


}
