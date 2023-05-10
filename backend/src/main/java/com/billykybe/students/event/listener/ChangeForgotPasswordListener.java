package com.billykybe.students.event.listener;

import com.billykybe.students.entity.UserAccount;
import com.billykybe.students.event.ChangeForgotPasswordEvent;
import com.billykybe.students.event.ChangePasswordEvent;
import com.billykybe.students.repository.ForgotPasswordTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChangeForgotPasswordListener implements ApplicationListener<ChangeForgotPasswordEvent> {
    @Autowired
    private ForgotPasswordTokenRepository forgotPasswordTokenRepository;
    @Override
    public void onApplicationEvent(ChangeForgotPasswordEvent event) {

        UserAccount userAccount =  event.getForgotPasswordToken().getUserAccount();

        // message

        // log
        forgotPasswordTokenRepository.delete(event.getForgotPasswordToken());

    }
}
