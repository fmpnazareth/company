package com.fmpnazareth.company.repository;

import com.fmpnazareth.company.domain.Role;
import com.fmpnazareth.company.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
