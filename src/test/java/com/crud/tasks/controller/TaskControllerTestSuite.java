package com.crud.tasks.controller;

import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldFetchEmptyTasksList() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        List<TaskDto> taskDto = new ArrayList<>();

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDto);

        //When &Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((status()).is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetTasks() throws Exception {
        //Given
        List<TaskDto> taskDto = new ArrayList<>();
        taskDto.add(new TaskDto(1L, "TaskNo1", "TaskNo1 content"));
        taskDto.add(new TaskDto(2L, "TaskNo2", "TaskNo2 content"));
        taskDto.add(new TaskDto(3L, "TaskNo3", "TaskNo3 content"));

        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDto);

        //When &Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status()).is(200)) // or isOK()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect((MockMvcResultMatchers.jsonPath("$.[0].id",Matchers.is(1))))
                .andExpect((MockMvcResultMatchers.jsonPath("$.[1].title",Matchers.is("TaskNo2"))))
                .andExpect((MockMvcResultMatchers.jsonPath("$.[2].content",Matchers.is("TaskNo3 content"))));
    }

    @Test
    void shouldGetTask() throws Exception{
        //Given
        Task task = new Task(1L, "TaskNo1", "TaskNo1 content");
        TaskDto dto = new TaskDto(1L, "mapped TaskNo1", "mapped TaskNo1 content");
        when(taskMapper.mapToTaskDto(task)).thenReturn(dto);
        when(dbService.getTask(anyLong())).thenReturn(Optional.of(task));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/task/getTask")
                .param("taskId", "1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("mapped TaskNo1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("mapped TaskNo1 content")));
    }


    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(1L, "TaskNo1", "TaskNo1 content");
        TaskDto taskDto = new TaskDto(1L, "TaskNo1", "TaskNo1 content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When &Then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent);
        mockMvc.perform(requestBuilder)
                .andExpect((MockMvcResultMatchers.status()).is2xxSuccessful());
    }

    @Test
    public void shouldUpdateTask()throws Exception{
        // Given
        TaskDto taskDto = new TaskDto(1L, "TaskNo1", "TaskNo1 content");
        TaskDto updatedTask = new TaskDto(1L, "TaskNo1 updated", "TaskNo1 updated content");

        when(taskMapper.mapToTaskDto(taskMapper.mapToTask(taskDto))).thenReturn(updatedTask);

        Gson gson = new Gson();
        String cont = gson.toJson(taskDto);


        //When & Then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(cont);
        mockMvc.perform(requestBuilder)
                .andExpect((MockMvcResultMatchers.status()).is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("TaskNo1 updated")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("TaskNo1 updated content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given, When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/task/deleteTask")
                        .param("taskId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}