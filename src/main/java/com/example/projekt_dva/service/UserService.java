package com.example.projekt_dva.service;

import com.example.projekt_dva.model.User;
import com.example.projekt_dva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoadFile loadFile;

    public User createUser(User user) {
        // Načtení personIDList ze souboru
        loadFile.loadPersonIDFromFile("dataPersonId.txt");
        List<String> personIds = loadFile.getPersonIDList();

        List<String> usedPersonIds = userRepository.findAll()
                .stream()
                .map(User::getPersonID)
                .collect(Collectors.toList());

        personIds.removeAll(usedPersonIds);

        if (personIds.isEmpty()) {
            throw new RuntimeException("No unique personID available");
        }

        user.setPersonID(personIds.get(0));
        user.setUuid(UUID.randomUUID().toString());

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setSurname(updatedUser.getSurname());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
