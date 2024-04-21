package com.huce.edu.services;


import com.huce.edu.entities.UserEntity;
import org.springframework.stereotype.Service;


@Service
public interface SendMailService {

    void forgetPasswordUser(UserEntity user, String otp);

    void registerUser(UserEntity user, String verificationCode);
}
