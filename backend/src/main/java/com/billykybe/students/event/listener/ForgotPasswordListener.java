package com.billykybe.students.event.listener;

import com.billykybe.students.event.ForgotPasswordEvent;
import com.billykybe.students.repository.ForgotPasswordTokenRepository;
import com.billykybe.students.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j
@Component
public class ForgotPasswordListener implements ApplicationListener<ForgotPasswordEvent> {
    @Autowired
    private ForgotPasswordTokenRepository forgotPasswordTokenRepository;
    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(ForgotPasswordEvent event) {

        String token  = UUID.randomUUID().toString();
        userService.saveTokenToUser(event.getAccount(),token);


        // log
        String message = event.getUrl()+"?token="+token;

        // send message

        log.info("Forgot password request received : "+message);


    }
}
