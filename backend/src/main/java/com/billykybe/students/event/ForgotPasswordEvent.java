package com.billykybe.students.event;

import com.billykybe.students.entity.UserAccount;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class ForgotPasswordEvent extends ApplicationEvent {
    private UserAccount account;
    private String url;
    public ForgotPasswordEvent(UserAccount userAccount,String url) {
        super(userAccount);
        this.account = userAccount;
        this.url = url;

    }
}
