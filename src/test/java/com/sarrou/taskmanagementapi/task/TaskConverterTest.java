package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TaskConverterTest {

    TaskConverter sut;
    @Mock
    private CategoryRepository categoryRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        sut = new TaskConverter(categoryRepository);
    }

    @Test
    void sutShouldConvertEntityToDto() {
        var category = new CategoryEntity(1L,"socializing");

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
        var category = new CategoryEntity(1L,"socializing");

        assertThatThrownBy(() -> sut.mapToDto(TaskEntity.builder()
                .taskId(1L).title("my title").category(category)
                .description("project").dueDate(LocalDate.now())
                .status(Task.StatusEnum.OPEN)
                .priority(Task.PriorityEnum.valueOf("wrong")).build()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sutShouldThrowExceptionWhenStatusIsNotValid() {

        var category = new CategoryEntity(1L,"socializing");

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

        var taskEntity = TaskEntity.builder()
                .taskId(1L).title("my title").category(categoryRepository.findCategoryEntityByName(taskDto.getCategoryName()))
                .description("project").dueDate(LocalDate.now())
                .status(Task.StatusEnum.OPEN).priority(Task.PriorityEnum.HIGH).build();

        var resultEntity = sut.mapToEntity(taskDto);

        assertThat(taskEntity).isEqualTo(resultEntity);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }


}