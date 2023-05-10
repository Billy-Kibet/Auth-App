package com.billykybe.students.repository;

import com.billykybe.students.entity.ERole;
import com.billykybe.students.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(ERole eRole);
}
