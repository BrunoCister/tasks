package com.api.tasks.controller;

import com.api.tasks.entity.Task;
import com.api.tasks.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void testCreateTask() {

        Task task = new Task(1L, "Tarefa AAA", "Descrção AAA", false, null);
        when(taskService.createTask(task)).thenReturn(new Task(1L, "Tarefa AAA", "Descrção AAA", false, null));

        Task createdTask = taskController.createTask(task);

        assertNotNull(createdTask.getId());
        assertEquals("Tarefa AAA", createdTask.getTitle());
        verify(taskService, times(1)).createTask(task);
    }

    @Test
    void testGetAllTasks() {

        when(taskService.getAllTasks()).thenReturn(List.of(new Task(1L, "Tarefa AAA", "Descrção AAA", false, null)));

        List<Task> tasks = taskController.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals("Tarefa AAA", tasks.getFirst().getTitle());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testGetTaskById() {

        Task task = new Task(1L, "Tarefa AAA", "Descrção AAA", false, null);
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<Task> response = taskController.getTaskById(1L);

        assertTrue(response.getBody() != null && response.getBody().getTitle().equals("Tarefa AAA"));
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void testDeleteTask() {

        doNothing().when(taskService).deleteTask(1L);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(taskService, times(1)).deleteTask(1L);
    }
}

