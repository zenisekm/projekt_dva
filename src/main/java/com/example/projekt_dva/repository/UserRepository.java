package com.example.projekt_dva.repository;

import com.example.projekt_dva.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

    public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByPersonID(String personID);
    }


