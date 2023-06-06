package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.TaskDto;
import com.sarrou.taskmanagementapi.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private com.sarrou.taskmanagementapi.task.service.Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

//    @Column(name = "CATEGORY_ID", insertable = false, updatable = false)
//    private Long categoryId;

    @Column(name = "DUE_DATE")
//    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY")
    private TaskDto.PriorityEnum priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
//    @Type(PostgreSQLEnumType.class)

    private TaskDto.StatusEnum status;



}
