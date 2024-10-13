package com.example.leaderboard.service;

import com.example.leaderboard.model.User;
import com.example.leaderboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.sort((u1, u2) -> u2.getScore().compareTo(u1.getScore())); // Sort users by score descending
        return users;
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserScore(String userId, Integer score) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setScore(score);
        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
