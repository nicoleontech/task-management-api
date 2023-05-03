package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.Address;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TaskConverterTest {

    TaskConverter sut;

    @BeforeEach
    void setUp() {
        sut = new TaskConverter();
    }

    @Test
    void sutShouldConvertEntityToDto() {
        Task taskDto = new Task();
        taskDto.setTaskId(1L);
        taskDto.setDescription("project");
        taskDto.setTitle("my title");
        taskDto.setDueDate(LocalDate.now());
        taskDto.setPriority(Task.PriorityEnum.HIGH);
        taskDto.setStatus(Task.StatusEnum.OPEN);

        var resultDto = sut.mapToDto(TaskEntity.builder()
                .taskId(1L).title("my title")
                .description("project").dueDate(LocalDate.now())
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH).build());

        assertThat(taskDto).isEqualTo(resultDto);
    }

    @Test
    void sutShouldThrowExceptionWhenPriorityIsNotValid() {
        assertThatThrownBy(() -> sut.mapToDto(TaskEntity.builder()
                .taskId(1L).title("my title")
                .description("project").dueDate(LocalDate.now())
                .status(Task.StatusEnum.OPEN)
                .priority(Task.PriorityEnum.valueOf("wrong")).build()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sutShouldThrowExceptionWhenStatusIsNotValid() {
        assertThatThrownBy(() -> sut.mapToDto(TaskEntity.builder()
                .taskId(1L).title("my title")
                .description("project").dueDate(LocalDate.now())
                .status(Task.StatusEnum.valueOf("i am funny"))
                .priority(Task.PriorityEnum.LOW)
                .build()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sutShouldConvertDtoToEntity() {
        var taskEntity = TaskEntity.builder()
                .taskId(1L).title("my title")
                .description("project").dueDate(LocalDate.now())
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH).build();

        Task taskDto = new Task();
        taskDto.setTaskId(1L);
        taskDto.setDescription("project");
        taskDto.setTitle("my title");
        taskDto.setDueDate(LocalDate.now());
        taskDto.setPriority(Task.PriorityEnum.HIGH);
        taskDto.setStatus(Task.StatusEnum.OPEN);

        var resultEntity = sut.mapToEntity(taskDto);

        assertThat(taskEntity).isEqualTo(resultEntity);
    }


}