package com.billykybe.students.repository;

import com.billykybe.students.entity.UserAccount;
import com.billykybe.students.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    boolean existsByToken(String token);

    VerificationToken findByToken(String token);

    VerificationToken findByUserAccount(UserAccount userAccount);
}
