package com.billykybe.students.event.listener;

import com.billykybe.students.entity.UserAccount;
import com.billykybe.students.event.VerificationCompleteEvent;
import com.billykybe.students.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VerificationCompleteListener implements ApplicationListener<VerificationCompleteEvent> {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Override
    public void onApplicationEvent(VerificationCompleteEvent event) {

        // log the event
        UserAccount userAccount = event.getVerificationToken().getUserAccount();

  verificationTokenRepository.delete(event.getVerificationToken());


        log.info("Account verified for " + userAccount.getEmail());


    }
}
