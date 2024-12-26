package com.portfolio.cms.modules.education;

import com.portfolio.cms.modules.education.dto.EducationCreateUpdateDto;
import com.portfolio.cms.modules.education.dto.EducationResponseDto;
import com.portfolio.core.common.ApiResponse;
import com.portfolio.core.security.AuthDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/education")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @GetMapping
    public ApiResponse<Page<EducationResponseDto>> getEducations(Pageable pageable) {
        return ApiResponse.success(educationService.findAll(pageable), "Educations fetched successfully");
    }

    @PostMapping
    public ApiResponse<EducationResponseDto> createEducation(@RequestBody @Valid EducationCreateUpdateDto educationCreateDto, AuthDetails authDetails){
        return ApiResponse.success(educationService.save(educationCreateDto, authDetails), "Education created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<EducationResponseDto> updateEducation(@PathVariable Integer id, @RequestBody @Valid EducationCreateUpdateDto educationUpdateDto, AuthDetails authDetails){
        return ApiResponse.success(educationService.update(id, educationUpdateDto, authDetails, false), "Education created successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<EducationResponseDto> deleteEducation(@PathVariable Integer id, AuthDetails authDetails){
        return ApiResponse.success(educationService.delete(id, authDetails), "Education deleted successfully");
    }
}
