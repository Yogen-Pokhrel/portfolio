package com.portfolio.user;

import com.portfolio.common.CommonService;
import com.portfolio.common.dto.PaginationDto;
import com.portfolio.common.exception.ResourceNotFoundException;
import com.portfolio.helpers.ListMapper;
import com.portfolio.role.RoleRepository;
import com.portfolio.role.dto.RoleResponseDto;
import com.portfolio.role.entity.Role;
import com.portfolio.user.dto.request.CreateUserDto;
import com.portfolio.user.dto.request.UpdateUserDto;
import com.portfolio.user.dto.response.UserDetailDto;
import com.portfolio.user.entity.User;
import com.portfolio.user.entity.UserStatus;
import com.portfolio.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements CommonService<CreateUserDto, UpdateUserDto, UserDetailDto, Integer> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final ListMapper<User, UserDetailDto> listMapper;

    @Override
    @SuppressWarnings("unchecked")
    public Page<UserDetailDto> findAll(PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getSize());
        Page<User> paginatedUser = userRepository.findAll(pageable);
        return paginatedUser.map(user -> modelMapper.map(user, UserDetailDto.class));
    }

    @SuppressWarnings("unchecked")
    public List<UserDetailDto> findAll() {
        return (List<UserDetailDto>) listMapper.mapList(userRepository.findAll(),new UserDetailDto());
    }

    @Override
    public UserDetailDto findById(Integer id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Exists with provided ID"));
        return modelMapper.map(user, UserDetailDto.class);
    }

    public UserDetailDto findByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if(user == null) {return null;}
        return modelMapper.map(user, UserDetailDto.class);
    }

    @Override
    public UserDetailDto save(CreateUserDto createDto) {

        String hashedPassword =new BCryptPasswordEncoder().encode(createDto.getPassword());
        createDto.setPassword(hashedPassword);
        User newUser = userRepository.save(modelMapper.map(createDto, User.class));

        return modelMapper.map(newUser, UserDetailDto.class);
    }

    @Override
    public UserDetailDto update(Integer userId, UpdateUserDto updateUserDto) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();

            if (updateUserDto.getFirstName() != null) {
                user.setFirstName(updateUserDto.getFirstName());
            }
            if (updateUserDto.getLastName() != null) {
                user.setLastName(updateUserDto.getLastName());
            }
            if (updateUserDto.getEmail() != null) {
                user.setEmail(updateUserDto.getEmail());
            }
            if (updateUserDto.getPhone() != null) {
                user.setPhone(updateUserDto.getPhone());
            }
            if (updateUserDto.getPassword() != null) {
                user.setPassword(updateUserDto.getPassword());
            }

            if(updateUserDto.getStatus() != null){
                UserStatus status = UserStatus.valueOf(updateUserDto.getStatus());
                user.setStatus(status);
            }

            if (updateUserDto.getImage() != null) {
                user.setImage(updateUserDto.getImage());
            }
            user = userRepository.save(user);
        } else {
            throw new Exception("User not found");
        }
        return modelMapper.map(user,UserDetailDto.class);
    }

    @Override
    public void delete(Integer id) throws Exception {
        findById(id);
        userRepository.deleteById(id);
    }

    /*public Page<UserDetailDto> findAllDeletedUsers(PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getSize());
        Page<User> paginatedUser = userRepository.findAllByDeletedTrue(pageable);
        return paginatedUser.map(user -> modelMapper.map(user, UserDetailDto.class));
    }*/
}
