package com.portfolio.auth.blogs;

import com.portfolio.auth.blogs.dto.request.CreateBlogDto;
import com.portfolio.auth.blogs.dto.request.UpdateBlogDto;
import com.portfolio.auth.blogs.dto.response.BlogResponseDto;
import com.portfolio.core.common.ApiResponse;
import com.portfolio.core.security.AuthDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/blogs")
public class BlogController {

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ApiResponse<Page<BlogResponseDto>> getBlogs(Pageable pageable, @AuthenticationPrincipal Jwt jwt) {
        System.out.println("Hello");
        AuthDetails authDetails = new AuthDetails(jwt);
        System.out.println(authDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("SecurityContext Authentication: " + authentication);
        return ApiResponse.success(blogService.findAll(pageable), "Blogs fetched successfully");
    }

    @PostMapping
    public ApiResponse<BlogResponseDto> create(@Valid @RequestBody CreateBlogDto createBlogDto, @AuthenticationPrincipal Jwt jwt, AuthDetails authDetails) {
        System.out.println("Hello");
        System.out.println("AuthDetails: " + authDetails);
//        @AuthenticationPrincipal Jwt jwt,
        jwt.getClaims().forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
//        blogService.save(createBlogDto, null)
        return ApiResponse.success(null, "Blog created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<BlogResponseDto> update(@PathVariable Integer id, @RequestBody UpdateBlogDto updateBlogDto) {
        return ApiResponse.success(blogService.update(id, updateBlogDto, null, false), "Blog updated successfully");
    }

    @PatchMapping("/{id}")
    public ApiResponse<BlogResponseDto> patchUpdate(@PathVariable Integer id, @RequestBody UpdateBlogDto updateBlogDto) {
        return ApiResponse.success(blogService.update(id, updateBlogDto, null, true), "Blog updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<BlogResponseDto> delete(@PathVariable Integer id) {
        return ApiResponse.success(blogService.delete(id, null), "Blog deleted successfully");
    }
}
