package com.portfolio.auth.role;

import com.portfolio.auth.common.ApiResponse;
import com.portfolio.auth.helpers.Utilities;
import com.portfolio.auth.role.dto.CreateRoleDto;
import com.portfolio.auth.role.dto.RoleResponseDto;
import com.portfolio.auth.role.dto.UpdateRoleDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    private ApiResponse<List<RoleResponseDto>> findAll(){
        System.out.println("RoleService" + roleService);
        List<RoleResponseDto> roles = roleService.findAll();
        return ApiResponse.success(roles, "Roles fetched successfully");
    }

    @GetMapping("/{id}")
    private ApiResponse<RoleResponseDto> findById(@PathVariable int id) throws Exception{
        String r = RoleAction.READ.getAction();
        RoleResponseDto roles = roleService.findById(id);
        return ApiResponse.success(roles, "Role fetched successfully");
    }

    @PostMapping
    private ApiResponse<RoleResponseDto> create(@Valid @RequestBody CreateRoleDto createRoleDto) throws Exception{
        createRoleDto.setSlug(Utilities.slugify(createRoleDto.getSlug(), true));
        RoleResponseDto newRole = roleService.save(createRoleDto);
        return ApiResponse.success(newRole, "Role created successfully");
    }

    @PutMapping("/{id}")
    private ApiResponse<RoleResponseDto> update(@PathVariable int id,@Valid @RequestBody UpdateRoleDto updateRoleDto) throws Exception{
        RoleResponseDto newRole = roleService.update(id, updateRoleDto);
        return ApiResponse.success(newRole, "Role updated successfully");
    }

    @DeleteMapping("/{id}")
    private ApiResponse<?> delete(@PathVariable int id) throws Exception{
        roleService.delete(id);
        return ApiResponse.success(null, "Role deleted successfully");
    }
}
