package com.portfolio.user;

import com.portfolio.common.ApiResponse;
import com.portfolio.common.ResponseMeta;
import com.portfolio.common.dto.PaginationDto;
import com.portfolio.common.exception.DuplicateResourceException;
import com.portfolio.helpers.FileUploaderService;
import com.portfolio.user.dto.request.CreateUserDto;
import com.portfolio.user.dto.request.UpdateUserDto;
import com.portfolio.user.dto.response.UserDetailDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;
    private final FileUploaderService fileUploaderService;
    private final String uploadPath = "public/portfolio/users/";

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    @GetMapping
    public ApiResponse<List<UserDetailDto>>  findAll(PaginationDto paginationDto) {
        Page<UserDetailDto> userPaginated = userService.findAll(paginationDto);
        return ApiResponse.success(
                userPaginated.getContent(),
                "Users fetched successfully",
                new ResponseMeta(
                        userPaginated.getNumber(),
                        userPaginated.getSize(),
                        userPaginated.getTotalElements(),
                        userPaginated.getTotalPages())
        );
    }

    @PostMapping(consumes = "multipart/form-data", produces = {"application/json"})
    public ApiResponse<UserDetailDto> createUser(@Valid @ModelAttribute CreateUserDto createUserDto) throws Exception {
        if(createUserDto.getUserImage() != null && !createUserDto.getUserImage().isEmpty()){
            File uploadedFile = fileUploaderService.upload(createUserDto.getUserImage(), uploadPath);
            createUserDto.setImage(uploadedFile.getPath());
        }
            UserDetailDto existingUser = userService.findByEmail(createUserDto.getEmail());
            if(existingUser != null) {
                throw new DuplicateResourceException("User already exists");
            }
            UserDetailDto newUser = userService.save(createUserDto);
            return ApiResponse.success(newUser, "User created successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDetailDto> findById(@PathVariable int id) throws Exception {
        return ApiResponse.success(userService.findById(id), "User fetched successfully");
    }

    @PutMapping(path = "/{id}", consumes = "multipart/form-data", produces = {"application/json"})
    public ApiResponse<UserDetailDto> update(@PathVariable int id, @ModelAttribute UpdateUserDto updateUserDto) throws Exception {
        if(updateUserDto.getUserImage() != null && !updateUserDto.getUserImage().isEmpty()){
            File uploadedFile = fileUploaderService.upload(updateUserDto.getUserImage(), uploadPath);
            updateUserDto.setImage(uploadedFile.getPath());
        }
        UserDetailDto userDetailDto = userService.update(id, updateUserDto);
        return ApiResponse.success(userDetailDto, "User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteUser(@PathVariable int id) throws Exception {
        userService.delete(id);
        return ApiResponse.success("Successfully Deleted the ", "true");
    }

    @QueryMapping("users")
    public List<UserDetailDto> getUsers() {
        return userService.findAll();
    }

    @QueryMapping("user")
    public UserDetailDto getUser(@RequestParam int id) throws Exception {
        System.out.println("Id " + id);
        return userService.findById(id);
    }
}
