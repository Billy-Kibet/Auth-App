package com.billykybe.students.event.listener;

import com.billykybe.students.event.RegistrationCompleteEvent;
import com.billykybe.students.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        String token = UUID.randomUUID().toString();


        String mail = "Verify you account with the link below "+ event.getUrl()+"?token="+token;

        userService.saveTokenToUser(token,event.getUserAccount());
        // send email
        log.info(mail);
    }
}
