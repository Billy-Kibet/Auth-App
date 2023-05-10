package com.billykybe.students.repository;

import com.billykybe.students.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount,Long> {
    boolean existsByEmail(String email);

    Optional<UserAccount> findByEmail(String email);


}
