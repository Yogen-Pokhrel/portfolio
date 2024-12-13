package com.portfolio.core.filter;

import com.portfolio.core.common.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {

        Collection<Permission> allPermissions = new ArrayList<>();
        if (permission instanceof Collection) {
            allPermissions = ((Collection<?>) permission).stream()
                    .filter(perm -> perm instanceof Permission)
                    .map(perm -> (Permission) perm)
                    .collect(Collectors.toList());
        }else if(permission instanceof Permission) {
            allPermissions.add((Permission) permission);
        }
        else {
            throw new IllegalArgumentException("Permission must be a Action which implements Permission or Collection<Permission>");
        }
        System.out.println("PermissionEvaluator has permissions: " + allPermissions);
        return true;
//        return rolePermissionService.checkPermission(auth, allPermissions);
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        // Additional logic can be added here if necessary
        return hasPermission(auth, targetType, permission);
    }
}
