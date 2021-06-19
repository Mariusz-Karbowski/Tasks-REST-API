package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.hibernate.query.criteria.internal.expression.SimpleCaseExpression;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DbServiceTestSuite {

    @InjectMocks
    DbService dbService;

    @Mock
    TaskRepository repository;

    @Test
    void testGetAllTasks() {
        //Given
        List<Task> tasks = List.of(new Task(1L, "TaskNo1", "TaskNo1Content"),
                new Task(2L, "TaskNo2", "TaskNo2Content"),
                new Task(3L, "TaskNo3", "TaskNo3Content"));
        when(repository.findAll()).thenReturn(tasks);

        //When
        List<Task> testTasks = dbService.getAllTasks();

        //Then
        assertNotNull(testTasks);
        assertEquals(testTasks.get(0).getId(), tasks.get(0).getId());
        assertEquals(testTasks.get(1).getTitle(), "TaskNo2");
        assertEquals(testTasks.get(2).getContent(), "TaskNo3Content");
    }

    @Test
    void testGetTask() {
        //Given
        List<Task> tasks = List.of(new Task(1L, "TaskNo1", "TaskNo1Content"),
                new Task(2L, "TaskNo2", "TaskNo2Content"),
                new Task(3L, "TaskNo3", "TaskNo3Content"));

        //When
        Optional<Task> testTask = dbService.getTask(1L);

        //Then
        assertEquals(1L, tasks.get(0).getId());
        assertEquals("TaskNo2", tasks.get(1).getTitle());
        assertEquals("TaskNo2Content", tasks.get(1).getContent());
    }

    @Test
    void testSaveTask() {
        //Given
        Task task = new Task(1L, "TaskNo1", "TaskNo1Content");

        when(repository.save(task)).thenReturn(task);

        //When
        Task testTask = dbService.saveTask(task);

        //Then
        assertEquals(task.getId(), testTask.getId());
        assertEquals(task.getTitle(), testTask.getTitle());
        assertEquals(task.getContent(), testTask.getContent());
    }

    @Test
    public void testGetAllTasksWithEmptyList() {
        //Given
        when(repository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<Task> fetchedTaskList = dbService.getAllTasks();

        //Then
        assertEquals(0, fetchedTaskList.size());
    }
}
