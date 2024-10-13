package com.example.leaderboard.service;

import com.example.leaderboard.model.User;
import com.example.leaderboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService();
        userService.userRepository = userRepository;
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User("1", "User1");
        user1.setScore(50);
        User user2 = new User("2", "User2");
        user2.setScore(30);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertEquals("User1", users.get(0).getUsername());
        assertEquals("User2", users.get(1).getUsername());
    }

    @Test
    public void testGetUserById() {
        User user = new User("1", "User1");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById("1");
        assertTrue(foundUser.isPresent());
        assertEquals("User1", foundUser.get().getUsername());
    }

    @Test
    public void testRegisterUser() {
        User user = new User("1", "User1");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerUser(user);
        assertEquals("User1", savedUser.getUsername());
    }
}
