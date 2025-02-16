package com.mohit.backend.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mohit.backend.auth.model.ForgetPassword;
import com.mohit.backend.auth.model.User;
public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword,Integer> {
    @Query("select fp from ForgetPassword where fp.otp= ?1 and fp.user =?2 ")
    Optional<ForgetPassword> findByOtpAndUser(Integer otp,User user);
}
