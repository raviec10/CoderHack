package com.example.leaderboard.controller;

import com.example.leaderboard.model.User;
import com.example.leaderboard.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserControllerTest {

    private MockMvc mockMvc;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = Mockito.mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("1", "User1"));
        users.add(new User("2", "User2"));

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("User1")))
                .andExpect(jsonPath("$[1].username", is("User2")));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User("1", "User1");

        Mockito.when(userService.getUserById("1")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/{userId}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("User1")));
    }

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User("1", "User1");
        Mockito.when(userService.registerUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"1\",\"username\":\"User1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("User1")));
    }
}
