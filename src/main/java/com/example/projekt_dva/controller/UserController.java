package com.example.projekt_dva.controller;

import com.example.projekt_dva.model.User;
import com.example.projekt_dva.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    /// test na web
    @GetMapping("/test")
    public String testEndpoint() {
        logger.info("Received request to test endpoint");
        return "Test endpoint is working!";
    }
    ///
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public User createUser(@RequestBody User user) throws IOException {
        return userService.createUser(user);
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam(required = false) boolean detail) {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


}