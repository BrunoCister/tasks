package com.api.tasks.service;

import com.api.tasks.entity.Project;
import com.api.tasks.repository.ProjectRepository;
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
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void testCreateProject() {

        Project project = new Project(1L, "Projeto AAA", "Descrição AAA", null);
        when(projectRepository.save(project)).thenReturn(new Project(1L, "Projeto AAA", "Descrição AAA", null));

        Project projectCriado = projectService.createProject(project);

        assertNotNull(projectCriado.getId());
        assertEquals("Projeto AAA", projectCriado.getName());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testGetAllProjects() {

        when(projectRepository.findAll()).thenReturn(List.of(new Project(1L, "Projeto AAA", "Descrição AAA", null)));

        List<Project> projects = projectService.getAllProjects();

        assertEquals(1, projects.size());
        assertEquals("Projeto AAA", projects.getFirst().getName());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetProjectById() {

        Project project = new Project(1L, "Projeto AAA", "Descrição AAA", null);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Optional<Project> foundProject = projectService.getProjectById(1L);

        assertTrue(foundProject.isPresent());
        assertEquals("Projeto AAA", foundProject.get().getName());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProject() {

        doNothing().when(projectRepository).deleteById(1L);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).deleteById(1L);
    }
}
