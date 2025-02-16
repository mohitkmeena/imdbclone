package com.mohit.backend.auth.controller;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mohit.backend.auth.model.ForgetPassword;
import com.mohit.backend.auth.model.User;
import com.mohit.backend.auth.repository.ForgetPasswordRepository;
import com.mohit.backend.auth.repository.UserRepository;
import com.mohit.backend.dto.MailBody;
import com.mohit.backend.service.EmailService;
import com.mohit.backend.auth.exception.ForgetPasswordException;
@RestController
@RequestMapping("/api/v1/passwd")
public class ForgetPasswordContoller {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
  private UserRepository userRepository;
    //send mail for veerification
   @Autowired
   private ForgetPasswordRepository forgetPasswordRepository;
    @Autowired
    private EmailService emailService;

    @PostMapping("/verify")
    public String verifyEmail(@RequestParam String email){
        User user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("enter correct email"));
        Integer otp=otpgenerator();
        MailBody mailBody=MailBody.builder()
        .to(email)
        .sub("otp verification")
        .body("otp to reset the password "+otp)
        .build();

        ForgetPassword forgetPassword=ForgetPassword.builder()
        .user(user)
        .otp(otp)
        .expirationtime(new Date(System.currentTimeMillis()+10*60*1000))
        .build();
        emailService.sendMail(mailBody);

        forgetPasswordRepository.save(forgetPassword);
        return "email sent successfully";

    }
    @PostMapping("/verifyotp")
    public String verifyotp(@RequestParam @NonNull Integer otp,@RequestParam @NonNull String email){
        User user =userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("enter a valid email"));
        ForgetPassword forgetPassword=forgetPasswordRepository.findByOtpAndUser(otp,user).orElseThrow(()->new ForgetPasswordException("request not found resent the otp"));
        if(forgetPassword.getExpirationtime().before(Date.from(Instant.now()))){
            forgetPasswordRepository.delete(forgetPassword);
            return "OTp EXPIRED";
        }

        return "OTP VERIFIED";
    }
    @PostMapping("/enter-passwd")
    public String changePasswd(@RequestParam String email,@RequestParam String passwd,@RequestParam String rpasswd){
        if(!Objects.equals(passwd, rpasswd)) return "password are  not matching";
        String password=passwordEncoder.encode(rpasswd);
        userRepository.updatePassword(passwd,email);
        return "password changed successfully";
    }


    private Integer otpgenerator(){
        Random random=new Random();
        return random.nextInt(000_000, 999_999);
    }
}
