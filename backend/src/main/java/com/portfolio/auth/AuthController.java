package com.portfolio.auth;

import com.portfolio.auth.dto.UserLoginDTO;
import com.portfolio.auth.dto.UserRegisterDTO;
import com.portfolio.auth.util.JWTUtil;
import com.portfolio.common.ApiResponse;
import com.portfolio.common.exception.DuplicateResourceException;
import com.portfolio.user.dto.request.CreateUserDto;
import com.portfolio.user.dto.response.UserDetailDto;
import com.portfolio.user.entity.User;
import com.portfolio.user.entity.UserStatus;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    private final BCryptPasswordEncoder passwordEncoder;

    private JWTUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AuthController(AuthService authService, BCryptPasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ApiResponse<UserDetailDto> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) throws Exception {

        if (this.authService.loadUserByUsername(userRegisterDTO.getEmail()) != null) {
            throw new DuplicateResourceException("Email already exists");
        }
        userRegisterDTO.setPassword(this.passwordEncoder.encode(userRegisterDTO.getPassword()));
        CreateUserDto createUserDto = this.modelMapper.map(userRegisterDTO, CreateUserDto.class);
        createUserDto.setStatus(UserStatus.APPROVED);
        return ApiResponse.success(
                this.authService.register(createUserDto),
                "User registered successfully"
        );
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO) throws Exception {
        User authenticatedUser = authService.validateLogin(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        AuthDetails authDetails = new AuthDetails(authenticatedUser);

        Map<String, Object> responseData = new HashMap<>();

        responseData.put("access_token", "Bearer " + this.jwtUtil.generateToken(authDetails));
        responseData.put("refresh_token", this.jwtUtil.generateRefreshToken(authDetails.getEmail()));
        responseData.put("userData", authService.getLoggedInUserData(authenticatedUser));

        return ApiResponse.success(
                responseData,
                "User logged in successfully"
        );
    }

    @GetMapping("/me")
    public ApiResponse<UserDetailDto> getCurrentUser(@AuthenticationPrincipal AuthDetails authDetails) throws Exception {
        UserDetailDto userData = authService.getLoggedInUserData(authDetails.getId());
        return ApiResponse.success(userData, "User data fetched successfully");
    }
}

