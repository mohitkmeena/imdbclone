package com.mohit.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mohit.backend.dto.MailBody;

@Service
public class EmailService {

    
    private JavaMailSender javaMailSender;
    
  @Value("${spring.mail.username}")
  private String sender;
    public void sendMail(MailBody mailBody){
          SimpleMailMessage mailMessage=new SimpleMailMessage();
          mailMessage.setTo(mailBody.to());
          mailMessage.setFrom(sender);
          mailMessage.setSubject(mailBody.sub());
          mailMessage.setText(mailBody.body());
          javaMailSender.send(mailMessage);
    }
}
