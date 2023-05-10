package com.billykybe.students.model;

import com.billykybe.students.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    // ROLE_ADMIN - admin
    // ROLE_USER - user
    private boolean isVerified ;
}
