package com.portfolio.account.rolePermission.repository;

import com.portfolio.account.rolePermission.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    
}
