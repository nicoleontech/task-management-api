package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import com.sarrou.taskmanagementapi.task.service.CategoryEntity;
import com.sarrou.taskmanagementapi.task.service.TaskEntity;
import com.sarrou.taskmanagementapi.task.web.TaskConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TaskConverterTest {

    TaskConverter sut;
    @Mock
    private CategoryRepository categoryRepository;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        sut = new TaskConverter(categoryRepository);
//    }

    @Test
    void sutShouldConvertEntityToDto() {
        var category = new CategoryEntity(1L, "socializing");

        Task taskDto = new Task();
        taskDto.setTaskId(1L);
        taskDto.setDescription("project");
        taskDto.setTitle("my title");
        taskDto.setCategoryName(category.getName());
        taskDto.setDueDate(LocalDate.now());
        taskDto.setPriority(Task.PriorityEnum.HIGH);
        taskDto.setStatus(Task.StatusEnum.OPEN);


        var resultDto = sut.mapToDto(TaskEntity.builder()
                .taskId(1L).title("my title").category(category)
                .description("project").dueDate(LocalDate.now())
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH).build());

        assertThat(taskDto).isEqualTo(resultDto);
    }

    @Test
    void sutShouldThrowExceptionWhenPriorityIsNotValid() {
        var category = new CategoryEntity(1L, "socializing");

        assertThatThrownBy(() -> sut.mapToDto(TaskEntity.builder()
                .taskId(1L).title("my title").category(category)
                .description("project").dueDate(LocalDate.now())
                .status(Task.StatusEnum.OPEN)
                .priority(Task.PriorityEnum.valueOf("wrong")).build()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sutShouldThrowExceptionWhenStatusIsNotValid() {

        var category = new CategoryEntity(1L, "socializing");

        assertThatThrownBy(() -> sut.mapToDto(TaskEntity.builder()
                .taskId(1L).title("my title").category(category)
                .description("project").dueDate(LocalDate.now())
                .status(Task.StatusEnum.valueOf("i am funny"))
                .priority(Task.PriorityEnum.LOW)
                .build()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sutShouldConvertDtoToEntity() {

        Task taskDto = new Task();
        taskDto.setTaskId(1L);
        taskDto.setDescription("project");
        taskDto.setTitle("my title");
        taskDto.setCategoryName("socializing");
        taskDto.setDueDate(LocalDate.now());
        taskDto.setPriority(Task.PriorityEnum.HIGH);
        taskDto.setStatus(Task.StatusEnum.OPEN);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskId(taskDto.getTaskId());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setCategory(categoryRepository.findByName(taskDto.getCategoryName()));
        taskEntity.setDueDate(taskDto.getDueDate());
        taskEntity.setPriority(taskDto.getPriority());
        taskEntity.setStatus(taskDto.getStatus());

        var resultEntity = sut.mapToEntity(taskDto);

        assertThat(resultEntity).isEqualTo(taskEntity);
    }


}