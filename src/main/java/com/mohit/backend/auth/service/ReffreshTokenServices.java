package com.mohit.backend.auth.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mohit.backend.auth.model.RefreshToken;
import com.mohit.backend.auth.model.User;
import com.mohit.backend.auth.repository.RefreshTokenRepo;
import com.mohit.backend.auth.repository.UserRepository;

@Service
public class ReffreshTokenServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired RefreshTokenRepo refreshTokenRepo;

    public RefreshToken createRefreshToken(String username){
        User user=userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("username not found"));
        RefreshToken refreshToken=user.getRefreshToken();
        if(refreshToken==null) {
            Long refreshTokenValidity=(long)20*24*60*60*10000;
            refreshToken=RefreshToken.builder()
            .refreshToken(UUID.randomUUID().toString())
            .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
            .user(user)
            .build();
            refreshTokenRepo.save(refreshToken);
        }
        return refreshToken;

    }

    public RefreshToken verifyRefreshToken(String refreshToken){
        RefreshToken refToken=refreshTokenRepo.FindByRefreshToken(refreshToken).orElseThrow(()-> new RuntimeException("refresh token not found"));

        if(refToken.getExpirationTime().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(refToken);
            throw new RuntimeException("refresh toen expired");
        }

        return refToken; 
    }

}
