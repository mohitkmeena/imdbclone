package com.mohit.backend.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mohit.backend.auth.model.RefreshToken;
import com.mohit.backend.auth.model.User;
import com.mohit.backend.auth.model.UserRole;
import com.mohit.backend.auth.repository.UserRepository;
import com.mohit.backend.auth.utils.AuthResponse;
import com.mohit.backend.auth.utils.LoginRequest;
import com.mohit.backend.auth.utils.RegisterRequest;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
 @Autowired
 private JwtService jwtService;
@Autowired
private ReffreshTokenServices reffreshTokenServices;
@Autowired
private AuthenticationManager manager;

public AuthResponse register(RegisterRequest registerRequest){
    var user= User.builder()
     .username(registerRequest.getUsername())
     .email(registerRequest.getEmail())
     .name(registerRequest.getName())
     .password(encoder.encode(registerRequest.getPassword()))
     .userRole(UserRole.USER)
     .build();
     User savedUser=userRepository.save(user);
     var accessToken=jwtService.generateToken(savedUser);
     var refreshToken=reffreshTokenServices.createRefreshToken(savedUser.getEmail());

     return AuthResponse.builder()
     .accessToken(accessToken)
     .refreshToken(refreshToken.getRefreshToken())
     .email(savedUser.getEmail())
     .name(savedUser.getName())
     .build();


}

public AuthResponse login(LoginRequest loginRequest){
   Authentication authenticate= manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
  
   
    User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UsernameNotFoundException("user name not found"));
     String accessToken=jwtService.generateToken(user);
     RefreshToken refreshToken=reffreshTokenServices.createRefreshToken(loginRequest.getEmail());
   
   return AuthResponse.builder()
   .accessToken(accessToken)
   .refreshToken(refreshToken.getRefreshToken())
   .name(user.getName())
   .email(loginRequest.getEmail())
   .build();
}


}
