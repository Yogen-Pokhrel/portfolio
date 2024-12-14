package com.portfolio.account.role;

import com.portfolio.account.role.dto.CreateRoleDto;
import com.portfolio.core.exception.ResourceNotFoundException;
import com.portfolio.core.helpers.ListMapper;
import com.portfolio.account.role.dto.RoleResponseDto;
import com.portfolio.account.role.dto.UpdateRoleDto;
import com.portfolio.account.role.entity.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ListMapper listMapper;

//    @Override
    @SuppressWarnings("unchecked")
    public List<RoleResponseDto> findAll() {
        return (List<RoleResponseDto>) listMapper.mapList(roleRepository.findAll(),new RoleResponseDto());
    }

//    @Override
    public RoleResponseDto findById(Integer id) throws Exception {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Role found with provided id"));
        return modelMapper.map(role, RoleResponseDto.class);
    }

//    @Override
    public RoleResponseDto save(CreateRoleDto createRoleDto) throws Exception {
       Role newRole = roleRepository.save(modelMapper.map(createRoleDto, Role.class));
       return modelMapper.map(newRole, RoleResponseDto.class);
    }

//    @Override
    public RoleResponseDto update(Integer id, UpdateRoleDto updateDto) throws Exception {
        Role existingRole = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Role found with provided id"));
        existingRole.setTitle(updateDto.getTitle());
        Role updatedRole = roleRepository.save(existingRole);
        return modelMapper.map(updatedRole, RoleResponseDto.class);
    }

//    @Override
    public void delete(Integer id) throws Exception {
        findById(id);
        roleRepository.deleteById(id);
    }
}
