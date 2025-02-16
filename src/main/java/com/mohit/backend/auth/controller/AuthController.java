package com.mohit.backend.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohit.backend.auth.model.RefreshToken;
import com.mohit.backend.auth.model.User;
import com.mohit.backend.auth.service.AuthFilterService;
import com.mohit.backend.auth.service.AuthService;
import com.mohit.backend.auth.service.JwtService;
import com.mohit.backend.auth.service.ReffreshTokenServices;
import com.mohit.backend.auth.utils.AuthResponse;
import com.mohit.backend.auth.utils.LoginRequest;
import com.mohit.backend.auth.utils.RefreshTokenReq;
import com.mohit.backend.auth.utils.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired 
    private AuthService authService;
    @Autowired
    private ReffreshTokenServices reffreshTokenServices;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest registerRequest){
       return  authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest){
   return authService.login(loginRequest);
    }
    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenReq refreshTokenReq){
           RefreshToken reftkn=reffreshTokenServices.verifyRefreshToken(refreshTokenReq.getRefreshtoken());
          User user= reftkn.getUser();
          String accessToken=jwtService.generateToken(user);
         return AuthResponse.builder()
         .name(user.getName())
         .email(user.getEmail())
         .accessToken(accessToken)
         .refreshToken(reftkn.getRefreshToken())
         .build();

    }

}
