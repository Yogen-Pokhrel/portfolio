package com.portfolio.auth.filter;

import com.portfolio.rolePermission.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    private  final RolePermissionService rolePermissionService;

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {

        Collection<String> allPermissions = new ArrayList<>();
        if (permission instanceof Collection) {
            allPermissions = ((Collection<?>) permission).stream()
                    .filter(perm -> perm instanceof String)
                    .map(perm -> (String) perm)
                    .collect(Collectors.toList());
        }else if (permission instanceof String) {
            allPermissions.add((String) permission);
        }else {
            throw new IllegalArgumentException("Permission must be a String or Collection<String>");
        }

        System.out.println(allPermissions);
        return rolePermissionService.checkPermission(auth, allPermissions);
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        // Additional logic can be added here if necessary
        return hasPermission(auth, targetType, permission);
    }
}
