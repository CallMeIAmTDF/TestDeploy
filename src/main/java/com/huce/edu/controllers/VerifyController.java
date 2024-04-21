package com.huce.edu.controllers;

import com.huce.edu.enums.VerificationEnum;
import com.huce.edu.services.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VerifyController {
    private final UserAccountService userAccountService;


    @GetMapping("/register/verify")
    public String verifyUser(@RequestParam(name = "code") String code) {
        VerificationEnum verificationEnum = userAccountService.verify(code);

        switch (verificationEnum) {
            case SUCCESS -> {
                return "verify_success";
            }
            case FAILED -> {
                return "verify_fail";
            }
            case TIME_OUT -> {
                return "verify_time_out";
            }
        }
        return null;
    }
}
