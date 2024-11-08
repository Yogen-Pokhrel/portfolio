package com.portfolio.rolePermission;

import com.portfolio.rolePermission.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    public boolean checkPermission(Authentication auth, Collection<String> permissions)
    {
     return true;
    }
}
