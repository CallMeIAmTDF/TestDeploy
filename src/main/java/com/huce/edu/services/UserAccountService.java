package com.huce.edu.services;

import com.huce.edu.entities.OtpEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.entities.VerificationcodeEntity;
import com.huce.edu.enums.RegisterEnum;
import com.huce.edu.enums.VerificationEnum;
import com.huce.edu.models.dto.ResetPasswordDto;
import com.huce.edu.models.dto.UpdateUserAccountDto;
import com.huce.edu.models.dto.UserAccountDto;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@Transactional
public interface UserAccountService {
    void save(UserEntity UserEntity);

    void edit (UpdateUserAccountDto updateUserAccountDto);

    boolean isTimeOutRequired(OtpEntity otpEntity, long ms);
    boolean isTimeOutRequired(VerificationcodeEntity VerificationcodeEntity, long ms);

    RegisterEnum register(UserAccountDto userAccountDto) throws MessagingException, NoSuchAlgorithmException;
    VerificationEnum verify(String code);
    void forgetPassword(int userId);
    VerificationEnum checkOtp(ResetPasswordDto resetPasswordDto);
}