package com.sarrou.taskmanagementapi.task.service;

import com.sarrou.taskmanagementapi.task.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService sut;

    @Test
    void findByName_returnsCategory_whenFound() {
        when(categoryRepository.findByName("Coding"))
                .thenReturn(Category.builder().name("Coding").build());
        var result = sut.findByName("Coding");
        assertThat(result).isNotNull();
        verify(categoryRepository, times(1))
                .findByName("Coding");

    }

}