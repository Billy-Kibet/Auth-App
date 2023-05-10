package com.billykybe.students.config;

import com.billykybe.students.entity.ERole;
import com.billykybe.students.entity.Role;
import com.billykybe.students.entity.UserAccount;
import com.billykybe.students.repository.RoleRepository;
import com.billykybe.students.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        // Create roles to db
        Role roleUser = new Role();
        roleUser.setName(ERole.ROLE_USER);

        Role roleAdmin = new Role();
        roleAdmin.setName(ERole.ROLE_ADMIN);

        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);



    }
}
