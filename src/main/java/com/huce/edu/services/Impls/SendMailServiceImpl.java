package com.huce.edu.services.Impls;


import com.huce.edu.entities.UserEntity;
import com.huce.edu.models.dto.DataMailDto;
import com.huce.edu.repositories.UserAccountRepo;
import com.huce.edu.services.MailService;
import com.huce.edu.services.SendMailService;
import com.huce.edu.shareds.Constants;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Component
@Service
@AllArgsConstructor
public class SendMailServiceImpl implements SendMailService {
    private MailService mailService;
    private UserAccountRepo userAccountRepo;

    private void putPropsDataMail(DataMailDto dataMail, String URL, String fullName, String email) throws MessagingException {
        Map<String, Object> props = new HashMap<>();
        props.put("fullname", fullName);
        props.put("email", email);
        props.put("URL", URL);
        dataMail.setProps(props);

        mailService.sendHtmlMail(dataMail, Constants.TEMPLATE_FILE_NAME.VERIFY_USER);
    }

    @Override
    @Async
    public void forgetPasswordUser(UserEntity user, String otp) {
        try {
            DataMailDto dataMail = new DataMailDto();

            dataMail.setTo(user.getEmail());
            dataMail.setSubject(Constants.SEND_MAIL_SUBJECT.USER_FORGET_PASSWORD);

            Map<String, Object> props = new HashMap<>();
            props.put("fullname", user.getName());
            props.put("email", user.getEmail());
            props.put("OTP", otp);
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Constants.TEMPLATE_FILE_NAME.USER_FORGET_PASSWORD);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void registerUser(UserEntity user, String verificationCode) {
        try {
            DataMailDto dataMail = new DataMailDto();

            dataMail.setTo(user.getEmail());
            dataMail.setSubject(Constants.SEND_MAIL_SUBJECT.USER_REGISTER);

            String URL = Constants.URL_VERIFICATION_CUSTOMER + verificationCode;

            putPropsDataMail(dataMail, URL, user.getName(), user.getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
