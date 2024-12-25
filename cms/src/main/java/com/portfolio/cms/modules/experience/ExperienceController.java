package com.portfolio.cms.modules.experience;

import com.portfolio.cms.modules.experience.dto.ExperienceCreateUpdateDto;
import com.portfolio.cms.modules.experience.dto.ExperienceResponseDto;
import com.portfolio.core.common.ApiResponse;
import com.portfolio.core.security.AuthDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/experience")
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
    public ApiResponse<ExperienceResponseDto> createExperience(@RequestBody @Valid ExperienceCreateUpdateDto createExperienceDto, AuthDetails authDetails) {
        return ApiResponse.success(this.experienceService.save(createExperienceDto, authDetails), "Experience created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<ExperienceResponseDto> updateExperience(@PathVariable Integer id, @RequestBody @Valid ExperienceCreateUpdateDto updateExperienceDto, AuthDetails authDetails) {
        return ApiResponse.success(this.experienceService.update(id, updateExperienceDto, authDetails, false), "Experience updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<ExperienceResponseDto> deleteExperience(@PathVariable Integer id, AuthDetails authDetails) {
        return ApiResponse.success(this.experienceService.delete(id, authDetails), "Experience deleted successfully");
    }
}
