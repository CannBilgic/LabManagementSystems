package com.example.ozguryazilimproje.repository.repository;

import com.example.ozguryazilimproje.repository.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
@Transactional
public interface RoleRepository  extends JpaRepository<Role,Long> {
}
