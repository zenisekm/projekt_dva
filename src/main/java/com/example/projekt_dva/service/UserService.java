package com.example.projekt_dva.service;

import com.example.projekt_dva.model.User;
import com.example.projekt_dva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) throws IOException {
        if (user.getPersonID() == null) {
            throw new IllegalStateException("PersonID cannot be null");
        }

        System.out.println("Creating user with personID: " + user.getPersonID());
        if (!isPersonIdValid(user.getPersonID())) {
            throw new IllegalStateException("PersonID is not valid");
        }

        if (userRepository.findByPersonID(user.getPersonID()).isPresent()) {
            throw new IllegalStateException("PersonID already exists");
        }

        user.setUuid(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    private boolean isPersonIdValid(String personID) throws IOException {
        List<String> validPersonIds = Files.readAllLines(Paths.get("dataPersonId.txt"));
        return validPersonIds.contains(personID);
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

            return userRepository.save(updatedUser);
        } else {
            throw new IllegalStateException("User not found");
        }
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

