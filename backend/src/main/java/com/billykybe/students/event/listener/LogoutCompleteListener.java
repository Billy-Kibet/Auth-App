package com.billykybe.students.event.listener;

import com.billykybe.students.entity.UserAccount;
import com.billykybe.students.event.LogoutCompleteEvent;
import com.billykybe.students.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogoutCompleteListener implements ApplicationListener<LogoutCompleteEvent> {
    private UserRepository userRepository;
    @Override
    public void onApplicationEvent(LogoutCompleteEvent event) {
        UserAccount userAccount =  userRepository.findByEmail(event.getUserAccount().getEmail()).get();
        //log
        log.info("User Account logout: " + userAccount);

    }
}
