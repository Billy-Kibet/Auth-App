package com.billykybe.students.event;

import com.billykybe.students.entity.UserAccount;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private final UserAccount userAccount;
    private final String  url;
    public RegistrationCompleteEvent(UserAccount userAccount, String url) {
        super(userAccount);
        this.userAccount = userAccount;
        this.url = url;


    }
}
