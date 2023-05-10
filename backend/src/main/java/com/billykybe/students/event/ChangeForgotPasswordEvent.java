package com.billykybe.students.event;

import com.billykybe.students.entity.ForgotPasswordToken;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class ChangeForgotPasswordEvent extends ApplicationEvent {
    private ForgotPasswordToken forgotPasswordToken;
    public ChangeForgotPasswordEvent(ForgotPasswordToken forgotPasswordToken) {
        super(forgotPasswordToken);
        this.forgotPasswordToken = forgotPasswordToken;
    }
}
