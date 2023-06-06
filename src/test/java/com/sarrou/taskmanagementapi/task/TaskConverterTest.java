package com.sarrou.taskmanagementapi.task;

import com.sarrou.api.Task;
import com.sarrou.api.TaskDto;
import org.junit.jupiter.api.AfterEach;
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

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        sut = new TaskConverter(categoryRepository);
    }

    @Test
    void sutShouldConvertEntityToDto() {
        var category = new CategoryEntity(1L,"socializing");

        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(1L);
        taskDto.setDescription("project");
        taskDto.setTitle("my title");
        taskDto.setCategoryName(category.getName());
        taskDto.setDueDate(LocalDate.now());
        taskDto.setPriority(TaskDto.PriorityEnum.HIGH);
        taskDto.setStatus(TaskDto.StatusEnum.OPEN);


        var resultDto = sut.mapToDto(Task.builder()
                .taskId(1L).title("my title").category(category)
                .description("project").dueDate(LocalDate.now())
                .status(TaskDto.StatusEnum.OPEN).priority(TaskDto.PriorityEnum.HIGH).build());

        assertThat(taskDto).isEqualTo(resultDto);
    }

    @Test
    void sutShouldThrowExceptionWhenPriorityIsNotValid() {
        var category = new CategoryEntity(1L,"socializing");

        assertThatThrownBy(() -> sut.mapToDto(Task.builder()
                .taskId(1L).title("my title").category(category)
                .description("project").dueDate(LocalDate.now())
                .status(TaskDto.StatusEnum.OPEN)
                .priority(TaskDto.PriorityEnum.valueOf("wrong")).build()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sutShouldThrowExceptionWhenStatusIsNotValid() {

        var category = new CategoryEntity(1L,"socializing");

        assertThatThrownBy(() -> sut.mapToDto(Task.builder()
                .taskId(1L).title("my title").category(category)
                .description("project").dueDate(LocalDate.now())
                .status(TaskDto.StatusEnum.valueOf("i am funny"))
                .priority(TaskDto.PriorityEnum.LOW)
                .build()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sutShouldConvertDtoToEntity() {


        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(1L);
        taskDto.setDescription("project");
        taskDto.setTitle("my title");
        taskDto.setCategoryName("socializing");
        taskDto.setDueDate(LocalDate.now());
        taskDto.setPriority(TaskDto.PriorityEnum.HIGH);
        taskDto.setStatus(TaskDto.StatusEnum.OPEN);

        var taskEntity = Task.builder()
                .taskId(1L).title("my title").category(categoryRepository.findCategoryEntityByName(taskDto.getCategoryName()))
                .description("project").dueDate(LocalDate.now())
                .status(TaskDto.StatusEnum.OPEN).priority(TaskDto.PriorityEnum.HIGH).build();

        var resultEntity = sut.mapToEntity(taskDto);

        assertThat(taskEntity).isEqualTo(resultEntity);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }


}