package com.api.tasks.service;

import com.api.tasks.entity.Task;
import com.api.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateTask() {

        Task task = new Task(1L, "Tarefa AAA", "Descrção AAA", false, null);
        when(taskRepository.save(task)).thenReturn(new Task(1L, "Tarefa AAA", "Descrção AAA", false, null));

        Task taskCriada = taskService.createTask(task);

        assertNotNull(taskCriada.getId());
        assertEquals("Tarefa AAA", taskCriada.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testGetAllTasks() {

        when(taskRepository.findAll()).thenReturn(List.of(new Task(1L, "Tarefa AAA", "Descrção AAA", false, null)));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals("Tarefa AAA", tasks.getFirst().getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById() {

        Task task = new Task(1L, "Tarefa AAA", "Descrção AAA", false, null);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTaskById(1L);

        assertTrue(foundTask.isPresent());
        assertEquals("Tarefa AAA", foundTask.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTasksByProjectId() {

        when(taskRepository.findByProjectId(1L)).thenReturn(List.of(new Task(1L, "Tarefa AAA", "Descrção AAA", false, null)));

        List<Task> tasks = taskService.getTasksByProjectId(1L);

        assertEquals(1, tasks.size());
        assertEquals("Tarefa AAA", tasks.getFirst().getTitle());
        verify(taskRepository, times(1)).findByProjectId(1L);
    }

    @Test
    void testDeleteTask() {

        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
