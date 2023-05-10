package com.billykybe.students.entity.log;

import com.billykybe.students.entity.UserAccount;
import jakarta.persistence.*;

import java.util.Date;

public class Log {

    private Long id;
    private Date date;
    private boolean isSystem;
    @Enumerated(value = EnumType.STRING)
    private ELog log;
    private String message;

    @OneToOne
    @JoinTable(name = "user_logs",joinColumns = @JoinColumn(name = "log_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private UserAccount userAccount;


}
