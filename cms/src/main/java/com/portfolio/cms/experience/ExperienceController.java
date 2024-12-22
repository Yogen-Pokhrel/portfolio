package com.portfolio.cms.experience;

import com.portfolio.cms.experience.dto.CreateExperienceDto;
import com.portfolio.cms.experience.dto.UpdateExperienceDto;
import com.portfolio.cms.experience.dto.ExperienceResponseDto;
import com.portfolio.core.common.ApiResponse;
import com.portfolio.core.security.AuthDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/experience")
public class ExperienceController {

    ExperienceService experienceService;

    @Autowired
    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    public ApiResponse<Page<ExperienceResponseDto>> getAllExperiences(Pageable pageable) {
        return ApiResponse.success(this.experienceService.findAll(pageable), "Experiences fetched successfully");
    }

    @PostMapping
    public ApiResponse<ExperienceResponseDto> createExperience(@RequestBody @Valid CreateExperienceDto createExperienceDto, AuthDetails authDetails) {
        return ApiResponse.success(this.experienceService.save(createExperienceDto, authDetails), "Experience created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<ExperienceResponseDto> updateExperience(@PathVariable Integer id, @RequestBody @Valid UpdateExperienceDto updateExperienceDto, AuthDetails authDetails) {
        return ApiResponse.success(this.experienceService.update(id, updateExperienceDto, authDetails, false), "Experience updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<ExperienceResponseDto> deleteExperience(@PathVariable Integer id, AuthDetails authDetails) {
        return ApiResponse.success(this.experienceService.delete(id, authDetails), "Experience deleted successfully");
    }
}
