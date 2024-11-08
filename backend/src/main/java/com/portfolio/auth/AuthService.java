package com.portfolio.auth;

import com.portfolio.common.exception.ResourceNotFoundException;
import com.portfolio.role.entity.Role;
import com.portfolio.user.UserService;
import com.portfolio.user.dto.request.CreateUserDto;
import com.portfolio.user.dto.response.UserDetailDto;
import com.portfolio.user.entity.User;
import com.portfolio.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AuthService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public AuthDetails loadUserByUsername(String username) {
        Optional<User> user = this.userRepository.findUserByEmail(username);
        return user.map(AuthDetails::new).orElse(null);
    }

    public UserDetailDto register(CreateUserDto user) {
        return userService.save(user);
    }

    public UserDetailDto getLoggedInUserData(int userId) throws Exception{
        User  u = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user found with provided id"));
        return modelMapper.map(u, UserDetailDto.class);
    }

    public UserDetailDto getLoggedInUserData(User user){
        return modelMapper.map(user, UserDetailDto.class);
    }

    public User validateLogin(String email, String password) throws Exception {
        Optional<User> user = this.userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            //Thread sleep is required to match the API response time, such that attacker doesn't have the information weather, it was because of incorrect password or user not available
            new BCryptPasswordEncoder().matches(password, "password_faker");
            throw new BadCredentialsException("Either provided email or password is incorrect");
        }
        if(!new BCryptPasswordEncoder().matches(password, user.get().getPassword())){
            throw new BadCredentialsException("Either provided email or password is incorrect");
        }
        return user.get();
    }
}
