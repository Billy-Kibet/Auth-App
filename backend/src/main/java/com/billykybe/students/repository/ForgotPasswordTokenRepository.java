package com.billykybe.students.repository;

import com.billykybe.students.entity.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken,Long> {
    ForgotPasswordToken findByToken(String token);

    boolean existsByToken(String token);
}
