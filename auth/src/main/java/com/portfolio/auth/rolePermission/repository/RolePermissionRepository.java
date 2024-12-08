package com.portfolio.auth.rolePermission.repository;

import com.portfolio.auth.rolePermission.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    
}
