package com.fmpnazareth.company.repository;

import com.fmpnazareth.company.repository.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
