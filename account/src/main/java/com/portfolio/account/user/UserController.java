package com.portfolio.auth.user;

import com.portfolio.core.helpers.FileUploaderService;
import com.portfolio.auth.user.dto.response.UserDetailDto;
import com.portfolio.auth.user.dto.request.CreateUserDto;
import com.portfolio.auth.user.dto.request.UpdateUserDto;
import com.portfolio.core.common.ApiResponse;
import com.portfolio.core.security.AuthDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;
    private final FileUploaderService fileUploaderService;
    private final String uploadPath = "public/portfolio/users/";

    @GetMapping
    public ApiResponse<Page<UserDetailDto>> findAll(Pageable pageable) {
        return ApiResponse.success(userService.findAll(pageable), "Users fetched successfully"
        );
    }

    @PostMapping(consumes = "multipart/form-data", produces = {"application/json"})
    public ApiResponse<UserDetailDto> createUser(@Valid @ModelAttribute CreateUserDto createUserDto, @AuthenticationPrincipal AuthDetails authDetails){
        if(createUserDto.getUserImage() != null && !createUserDto.getUserImage().isEmpty()){
            File uploadedFile = fileUploaderService.upload(createUserDto.getUserImage(), uploadPath);
            createUserDto.setImage(uploadedFile.getPath());
        }
        return ApiResponse.success(userService.save(createUserDto, authDetails), "User created successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDetailDto> findById(@PathVariable int id) throws Exception {
        return ApiResponse.success(userService.findById(id), "User fetched successfully");
    }

    @PutMapping(path = "/{id}", consumes = "multipart/form-data", produces = {"application/json"})
    public ApiResponse<UserDetailDto> update(@PathVariable int id, @ModelAttribute UpdateUserDto updateUserDto, AuthDetails authDetails) {
        if(updateUserDto.getUserImage() != null && !updateUserDto.getUserImage().isEmpty()){
            File uploadedFile = fileUploaderService.upload(updateUserDto.getUserImage(), uploadPath);
            updateUserDto.setImage(uploadedFile.getPath());
        }
        UserDetailDto userDetailDto = userService.update(id, updateUserDto, authDetails, false);
        return ApiResponse.success(userDetailDto, "User updated successfully");
    }

    @PatchMapping(path = "/{id}", consumes = "multipart/form-data", produces = {"application/json"})
    public ApiResponse<UserDetailDto> patchUpdate(@PathVariable int id, @ModelAttribute UpdateUserDto updateUserDto, AuthDetails authDetails) {
        if(updateUserDto.getUserImage() != null && !updateUserDto.getUserImage().isEmpty()){
            File uploadedFile = fileUploaderService.upload(updateUserDto.getUserImage(), uploadPath);
            updateUserDto.setImage(uploadedFile.getPath());
        }
        UserDetailDto userDetailDto = userService.update(id, updateUserDto, authDetails, true);
        return ApiResponse.success(userDetailDto, "User updated successfully");
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasPermission(null, T(com.portfolio.auth.user.UserAction).DELETE)")
    public ApiResponse<UserDetailDto> deleteUser(@PathVariable int id, @AuthenticationPrincipal AuthDetails authDetails) {
        return ApiResponse.success( userService.delete(id, authDetails), "User deleted successfully");
    }
}
