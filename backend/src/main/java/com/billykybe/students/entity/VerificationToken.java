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
public class VerificationToken {
    @Id
    @SequenceGenerator(name = "verification_token_sequence",sequenceName = "verification_token_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "verification_token_sequence")
    private Long id;
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "verification_token",joinColumns = @JoinColumn(name = "token_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private UserAccount userAccount;
    private Date expirationDate;
    public VerificationToken(UserAccount userAccount,String token){

        this.userAccount = userAccount;
        this.token = token;
        this.expirationDate = createExpirationDate();
    }
    private  Date createExpirationDate(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        calendar.add(Calendar.MINUTE,10);
        return new Date(calendar.getTime().getTime());

        // Default expiration date is 10 minutes

    }
}
