package com.billykybe.students.event;

import com.billykybe.students.entity.UserAccount;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter

public class LogoutCompleteEvent extends ApplicationEvent {
    private UserAccount userAccount;
    public LogoutCompleteEvent(UserAccount userAccount) {
        super(userAccount);
        this.userAccount = userAccount;
    }
}
