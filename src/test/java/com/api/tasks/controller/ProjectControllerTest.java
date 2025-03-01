package com.api.tasks.controller;

import com.api.tasks.entity.Project;
import com.api.tasks.service.ProjectService;
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
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    void testCreateProject() {

        Project project = new Project(1L, "Projeto AAA", "Descrição AAA", null);
        when(projectService.createProject(project)).thenReturn(new Project(1L, "Projeto AAA", "Descrição AAA", null));

        Project projectCriado = projectController.createProject(project);

        assertNotNull(projectCriado.getId());
        assertEquals("Projeto AAA", projectCriado.getName());
        verify(projectService, times(1)).createProject(project);
    }

    @Test
    void testGetAllProjects() {

        when(projectService.getAllProjects()).thenReturn(List.of(new Project(1L, "Projeto AAA", "Descrição AAA", null)));

        List<Project> projects = projectController.getAllProjects();

        assertEquals(1, projects.size());
        assertEquals("Projeto AAA", projects.getFirst().getName());
        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void testGetProjectById() {

        Project project = new Project(1L, "Projeto AAA", "Descrição AAA", null);
        when(projectService.getProjectById(1L)).thenReturn(Optional.of(project));

        ResponseEntity<Project> response = projectController.getProjectById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Projeto AAA", response.getBody().getName());
        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    void testDeleteProject() {

        doNothing().when(projectService).deleteProject(1L);

        ResponseEntity<Void> response = projectController.deleteProject(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(projectService, times(1)).deleteProject(1L);
    }
}
