package com.mohit.backend.dto;

import lombok.Builder;

@Builder
public record MailBody(String to,String sub,String body) {
    
}
