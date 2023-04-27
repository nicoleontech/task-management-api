package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

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
    void sutShouldConvertEntityToDtoWithTitleAndDescription(){
        var resultDto = sut.mapToDto(TaskEntity.builder()
                .taskId(1L).dueDate(LocalDate.now())
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH).build());

        Task taskDto = new Task();
        taskDto.setTaskId(1L);
        taskDto.setDescription(null);
        taskDto.setTitle(null);
        taskDto.setDueDate(LocalDate.now());
        taskDto.setPriority(Task.PriorityEnum.HIGH);
        taskDto.setStatus(Task.StatusEnum.OPEN);

        assertThat(taskDto).isEqualTo(resultDto);
    }

    @Test
    void sutShouldConvertEntityToDtoWhenStatusIsValid(){
        return;
    }

    @Test
    void sutShouldConvertEntityToDtoWhenPriorityIsValid(){
        return;
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