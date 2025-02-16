package com.mohit.backend.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mohit.backend.auth.model.User;

import jakarta.transaction.Transactional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User>findByEmail(String username);
    
    @Transactional
    @Modifying
    @Query("update User u set u.password=?1 where u.email=?2")
    void updatePassword(String passwd, String email);
}
