package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "TaskNo1", "TaskNo1Content");

        //When
        Task taskNo1 = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(1L, taskNo1.getId());
        assertEquals("TaskNo1", taskNo1.getTitle());
        assertEquals("TaskNo1Content", taskNo1.getContent());
    }

    @Test
    void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "TaskNo1", "TaskNo1Content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(1L, taskDto.getId());
        assertEquals("TaskNo1", taskDto.getTitle());
        assertEquals("TaskNo1Content", taskDto.getContent());
    }

    @Test
    void testMapToTaskDtoList() {
        //Given
        Task taskNo1 = new Task(1L, "TaskNo1", "TaskNo1Content");
        Task taskNo2 = new Task(2L, "TaskNo2", "TaskNo2Content");
        Task taskNo3 = new Task(3L, "TaskNo3", "TaskNo3Content");

        List<Task> taskList = Arrays.asList(taskNo1, taskNo2, taskNo3);

        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(3, taskDtos.size());
        assertEquals(1L, taskDtos.get(0).getId());
        assertEquals("TaskNo2", taskDtos.get(1).getTitle());
        assertEquals("TaskNo3Content", taskDtos.get(2).getContent());
    }
}
