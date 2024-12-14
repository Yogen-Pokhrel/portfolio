package com.portfolio.account.blogs;

import com.portfolio.account.blogs.dto.request.CreateBlogDto;
import com.portfolio.account.blogs.dto.request.UpdateBlogDto;
import com.portfolio.account.blogs.dto.response.BlogResponseDto;
import com.portfolio.core.common.ApiResponse;
import com.portfolio.core.security.AuthDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/blogs")
@Tag(name = "Blog", description = "Operations related to blogs")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ApiResponse<Page<BlogResponseDto>> getBlogs(Pageable pageable) {
        return ApiResponse.success(blogService.findAll(pageable), "Blogs fetched successfully");
    }

    @PostMapping
    @Operation(description = "Create blog", summary = "create blogs")
    public ApiResponse<BlogResponseDto> create(@Valid @RequestBody CreateBlogDto createBlogDto, AuthDetails authDetails) {
        blogService.save(createBlogDto, authDetails);
        return ApiResponse.success(null, "Blog created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<BlogResponseDto> update(@PathVariable Integer id, @RequestBody UpdateBlogDto updateBlogDto) {
        return ApiResponse.success(blogService.update(id, updateBlogDto, null, false), "Blog updated successfully");
    }

    @PatchMapping("/{id}")
    public ApiResponse<BlogResponseDto> patchUpdate(@PathVariable Integer id, @RequestBody UpdateBlogDto updateBlogDto, AuthDetails authDetails) {
        return ApiResponse.success(blogService.update(id, updateBlogDto, authDetails, true), "Blog updated successfully");
    }

    @PatchMapping("/{id}/approve")
    public ApiResponse<BlogResponseDto> approve(@PathVariable Integer id, @RequestBody UpdateBlogDto updateBlogDto, AuthDetails authDetails) {
        return ApiResponse.success(blogService.update(id, updateBlogDto, authDetails, true), "Blog approved successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<BlogResponseDto> delete(@PathVariable Integer id) {
        return ApiResponse.success(blogService.delete(id, null), "Blog deleted successfully");
    }
}
