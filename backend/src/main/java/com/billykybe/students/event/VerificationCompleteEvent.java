package com.billykybe.students.event;

import com.billykybe.students.entity.UserAccount;
import com.billykybe.students.entity.VerificationToken;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class VerificationCompleteEvent extends ApplicationEvent {
    private VerificationToken verificationToken;
    public VerificationCompleteEvent(VerificationToken verificationToken) {
        super(verificationToken);
this.verificationToken = verificationToken;
    }
}
