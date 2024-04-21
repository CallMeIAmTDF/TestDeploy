package com.huce.edu.services;

import com.huce.edu.models.dto.DataMailDto;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendHtmlMail(DataMailDto dataMail, String templateName) throws MessagingException;
}