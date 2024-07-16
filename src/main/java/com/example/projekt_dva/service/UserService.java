package com.example.projekt_dva.service;

import com.example.projekt_dva.model.User;
import com.example.projekt_dva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) throws IOException {
        if (user.getPersonID() == null) {
            throw new IllegalArgumentException("PersonID cannot be null");
        }

        if (!isPersonIdValid(user.getPersonID())) {
            throw new IllegalArgumentException("PersonID is not valid");
        }

        if (userRepository.findByPersonID(user.getPersonID()).isPresent()) {
            throw new IllegalArgumentException("PersonID already exists");
        }

        user.setUuid(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    private boolean isPersonIdValid(String personID) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("dataPersonId.txt")))) {
            List<String> validPersonIds = reader.lines().collect(Collectors.toList());
            return validPersonIds.contains(personID);
        } catch (NullPointerException e) {
            throw new IOException("dataPersonId.txt file not found", e);
        }
    }

    public User getUserById(Long id, boolean detail) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (detail) {
                return user.get();
            } else {
                User userWithoutDetails = new User();
                userWithoutDetails.setId(user.get().getId());
                userWithoutDetails.setName(user.get().getName());
                userWithoutDetails.setSurname(user.get().getSurname());
                return userWithoutDetails;
            }
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
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
            return userRepository.save(updatedUser);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
