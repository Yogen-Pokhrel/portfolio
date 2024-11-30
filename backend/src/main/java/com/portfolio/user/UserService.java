package com.portfolio.user;

import com.portfolio.auth.AuthDetails;
import com.portfolio.common.crud.CrudService;
import com.portfolio.common.exception.ValidationException;
import com.portfolio.user.dto.request.CreateUserDto;
import com.portfolio.user.dto.request.UpdateUserDto;
import com.portfolio.user.dto.response.UserDetailDto;
import com.portfolio.user.entity.User;
import com.portfolio.user.entity.UserStatus;
import com.portfolio.user.repository.UserRepository;
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
