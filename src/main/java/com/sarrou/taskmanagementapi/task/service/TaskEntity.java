package com.sarrou.taskmanagementapi.task.service;

import com.sarrou.api.Task;
import com.sarrou.taskmanagementapi.user.User;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
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
@Table(name = "tasks")
public class TaskEntity {

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
    private CategoryEntity category;

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
//    @Type(PostgreSQLEnumType.class)
    private Task.PriorityEnum priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
//    @Type(PostgreSQLEnumType.class)
    private Task.StatusEnum status;


}
