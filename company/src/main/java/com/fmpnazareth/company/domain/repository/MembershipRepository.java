package com.fmpnazareth.company.domain.repository;

import com.fmpnazareth.company.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {
}
