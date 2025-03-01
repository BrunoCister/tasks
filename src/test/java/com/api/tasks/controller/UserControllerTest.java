package com.api.tasks.controller;

import com.api.tasks.entity.User;
import com.api.tasks.service.UserService;
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
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testCreateUser() {

        User user = new User(1L, "Joao", "joao@mail.com", "12345", null);
        when(userService.createUser(user)).thenReturn(new User(1L, "Joao", "joao@mail.com", "12345", null));

        User userCriado = userController.createUser(user);

        assertNotNull(userCriado.getId());
        assertEquals("Joao", userCriado.getUsername());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testGetAllUsers() {

        when(userService.getAllUsers()).thenReturn(List.of(new User(1L, "Joao", "joao@mail.com", "12345", null)));

        List<User> users = userController.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("Joao", users.getFirst().getUsername());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {

        User user = new User(1L, "Joao", "joao@mail.com", "12345", null);
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Joao", response.getBody().getUsername());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testDeleteUser() {

        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(1L);
    }
}
