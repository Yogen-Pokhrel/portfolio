package com.portfolio.auth.user;

import com.portfolio.auth.user.dto.response.UserDetailDto;
import com.portfolio.auth.user.entity.User;
import com.portfolio.auth.user.entity.UserStatus;
import com.portfolio.auth.user.repository.UserRepository;
import com.portfolio.auth.user.dto.request.CreateUserDto;
import com.portfolio.auth.user.dto.request.UpdateUserDto;
import com.portfolio.core.exception.ValidationException;
import com.portfolio.core.security.AuthDetails;
import com.portfolio.core.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService extends CrudService<UserRepository, User, CreateUserDto, UpdateUserDto, UserDetailDto, Integer> {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper){
        super(userRepository, User.class, UserDetailDto.class);
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDetailDto findByEmail(String email) {
        log.info("Find user by email: {}", email);
        User user = userRepository.findUserByEmail(email).orElse(null);
        if(user == null) {return null;}
        return modelMapper.map(user, UserDetailDto.class);
    }

    @Override
    public UserDetailDto save(CreateUserDto createDto, AuthDetails authDetails) {
        log.info("Saving user {}", createDto.getEmail());
        String hashedPassword = new BCryptPasswordEncoder().encode(createDto.getPassword());
        createDto.setPassword(hashedPassword);
        createDto.setStatus(UserStatus.APPROVED);
        return super.save(createDto, authDetails);
    }

    @Override
    public UserDetailDto update(Integer id, UpdateUserDto updateUserDto, AuthDetails authDetails, Boolean isPatchRequest) {
        log.info("Updating user {}", id);
        if(updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
            String hashedPassword =new BCryptPasswordEncoder().encode(updateUserDto.getPassword());
            updateUserDto.setPassword(hashedPassword);
        }
        if(updateUserDto.getStatus() != null){
            updateUserDto.setStatus(UserStatus.APPROVED);
        }
        return super.update(id, updateUserDto, authDetails, isPatchRequest);
    }

    @Override
    protected void validate(User entity) throws ValidationException {
        if(entity.getEmail() != null && !entity.getEmail().isEmpty()) {
            Optional<User> existingRecord;
            if(entity.getId() != null){
                existingRecord = userRepository.findUserByEmailIgnoreCaseAndIdNot(entity.getEmail(), entity.getId());
            }else{
                existingRecord = userRepository.findUserByEmailIgnoreCase(entity.getEmail());
            }
            if(existingRecord.isPresent()){
                throw new ValidationException("Email already exists");
            }
        }
        super.validate(entity);
    }
}
