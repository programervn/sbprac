package com.thaipd.sbprac.security.repository;

import com.thaipd.sbprac.entity.ERole;
import com.thaipd.sbprac.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
