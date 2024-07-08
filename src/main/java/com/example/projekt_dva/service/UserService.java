package com.example.projekt_dva.service;

import com.example.projekt_dva.model.User;
import com.example.projekt_dva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        if (user.getPersonID() == null) {
            throw new IllegalStateException("PersonID cannot be null");
        }

        // Log personID for debugging
        System.out.println("Creating user with personID: " + user.getPersonID());

        // Check if personID already exists
        if (userRepository.findByPersonID(user.getPersonID()).isPresent()) {
            throw new IllegalStateException("PersonID already exists");
        }

        // Generate UUID for new user
        user.setUuid(UUID.randomUUID().toString());

        // Save new user
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setSurname(user.getSurname());
            // No need to update personID and UUID
            return userRepository.save(updatedUser);
        } else {
            throw new IllegalStateException("User not found");
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

