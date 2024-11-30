package com.portfolio.rolePermission;

import com.portfolio.auth.AuthDetails;
import com.portfolio.common.Permission;
import com.portfolio.rolePermission.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    public boolean checkPermission(Authentication auth,  Collection<Permission> permissions)
    {
        log.info("{} {}", auth.getPrincipal(), auth.getAuthorities());
        if(!(auth.getPrincipal() instanceof AuthDetails)){
            return false;
        }
        permissions.forEach((e) -> System.out.println(e.getAction() + " " + e.getDomain()));
     return true;
    }
}
