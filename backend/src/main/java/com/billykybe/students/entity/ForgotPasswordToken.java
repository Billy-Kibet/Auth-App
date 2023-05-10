package com.billykybe.students.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordToken {


    @Id
    @SequenceGenerator(name = "verification_token_sequence",sequenceName = "verification_token_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "verification_token_sequence")
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "forgot_password_token",joinColumns = @JoinColumn(name = "token_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private UserAccount userAccount;
    private Date expirationDate;
    public ForgotPasswordToken(UserAccount userAccount,String token){

        this.userAccount = userAccount;
        this.token = token;
        this.expirationDate = createExpirationDate();
    }
    private  Date createExpirationDate(){
        // Default expiration date is 10 minutes


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        calendar.add(Calendar.MINUTE,10);
        return new Date(calendar.getTime().getTime());
    }
}
