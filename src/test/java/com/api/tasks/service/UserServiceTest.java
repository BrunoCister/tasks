package com.api.tasks.service;

import com.api.tasks.entity.User;
import com.api.tasks.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {

        User user = new User(1L, "Joao", "joao@mail.com", "12345", null);
        when(userRepository.save(user)).thenReturn(new User(1L, "Joao", "joao@mail.com", "12345", null));

        User userCriado = userService.createUser(user);

        assertNotNull(userCriado.getId());
        assertEquals("Joao", userCriado.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById() {

        User user = new User(1L, "Joao", "joao@mail.com", "12345", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> userEncontrado = userService.getUserById(1L);

        assertTrue(userEncontrado.isPresent());
        assertEquals("Joao", userEncontrado.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser() {

        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
