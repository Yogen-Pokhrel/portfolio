package com.portfolio.systemModule;

import com.portfolio.auth.AuthDetails;
import com.portfolio.common.ApiResponse;
import com.portfolio.common.exception.ResourceNotFoundException;
import com.portfolio.systemModule.dto.request.SystemModuleCreateDto;
import com.portfolio.systemModule.dto.request.SystemModuleUpdateDto;
import com.portfolio.systemModule.dto.response.SystemModuleResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/system-modules")
public class SystemModuleController {
    private final SystemModuleService systemModuleService;
    public SystemModuleController(SystemModuleService systemModuleService) {
        this.systemModuleService = systemModuleService;
    }

    @GetMapping
    @PreAuthorize("hasPermission(null, T(com.portfolio.systemModule.SystemModuleAction).READ)")
    public ApiResponse<Page<SystemModuleResponseDto>> getAllSystemModules(Pageable pageable) {
        return ApiResponse.success(systemModuleService.findAll(pageable), "System Modules Fetched Successfully");
    }

    @PostMapping
    @PreAuthorize("hasPermission(null, T(com.portfolio.systemModule.SystemModuleAction).CREATE)")
    public ApiResponse<SystemModuleResponseDto> create(@Valid @RequestBody SystemModuleCreateDto systemModuleCreateDto, @AuthenticationPrincipal AuthDetails authDetails){
        return ApiResponse.success(systemModuleService.save(systemModuleCreateDto), "System Module Created Successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasPermission(null, T(com.portfolio.systemModule.SystemModuleAction).UPDATE)")
    public ApiResponse<SystemModuleResponseDto> update(@PathVariable @Valid Integer id, @Valid @RequestBody SystemModuleUpdateDto systemModuleUpdateDto) throws ResourceNotFoundException {
        return ApiResponse.success(systemModuleService.update(id, systemModuleUpdateDto, false), "System Module Updated Successfully");
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasPermission(null, T(com.portfolio.systemModule.SystemModuleAction).UPDATE)")
    public ApiResponse<SystemModuleResponseDto> patchUpdate(@PathVariable @Valid Integer id, @RequestBody SystemModuleUpdateDto systemModuleUpdateDto) throws ResourceNotFoundException {
        return ApiResponse.success(systemModuleService.update(id, systemModuleUpdateDto, true), "System Module Updated Successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(null, T(com.portfolio.systemModule.SystemModuleAction).DELETE)")
    public ApiResponse<SystemModuleResponseDto> delete(@PathVariable @Valid Integer id) throws ResourceNotFoundException {
        return ApiResponse.success(systemModuleService.delete(id), "System Module Deleted Successfully");
    }
}
