package com.portfolio.cms.experience;

import com.portfolio.cms.experience.entity.Experience;
import com.portfolio.cms.experience.dto.CreateExperienceDto;
import com.portfolio.cms.experience.dto.UpdateExperienceDto;
import com.portfolio.cms.experience.dto.ExperienceResponseDto;
import com.portfolio.core.exception.ValidationException;
import com.portfolio.core.security.AuthDetails;
import com.portfolio.core.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceService extends CrudService<ExperienceRepository, Experience, CreateExperienceDto, UpdateExperienceDto, ExperienceResponseDto, Integer> {

    @Autowired
    public ExperienceService(ExperienceRepository repository) {
        super(repository, Experience.class, ExperienceResponseDto.class);
    }

    @Override
    public ExperienceResponseDto save(CreateExperienceDto createDto, AuthDetails authDetails) throws ValidationException {
        Experience experience = new Experience();
        return super.save(createDto, authDetails);
    }
}
