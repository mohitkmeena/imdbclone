package com.mohit.backend.auth.utils;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequest {
    private String email;
   
    private String password;
}
