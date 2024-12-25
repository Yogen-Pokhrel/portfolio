package com.portfolio.cms.modules.experience;

import com.portfolio.cms.modules.experience.entity.Experience;
import com.portfolio.cms.modules.experience.dto.ExperienceCreateUpdateDto;
import com.portfolio.cms.modules.experience.dto.ExperienceResponseDto;
import com.portfolio.core.exception.ResourceNotFoundException;
import com.portfolio.core.exception.ValidationException;
import com.portfolio.core.security.AuthDetails;
import com.portfolio.core.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceService extends CrudService<ExperienceRepository, Experience, ExperienceCreateUpdateDto, ExperienceResponseDto, Integer> {

    @Autowired
    public ExperienceService(ExperienceRepository repository) {
        super(repository, Experience.class, ExperienceResponseDto.class);
    }

    @Override
    public ExperienceResponseDto save(ExperienceCreateUpdateDto createDto, AuthDetails authDetails) throws ValidationException {
        createDto.setUserId(authDetails.getUserId());
        return super.save(createDto, authDetails);
    }

    @Override
    public ExperienceResponseDto update(Integer id, ExperienceCreateUpdateDto updateDto, AuthDetails authDetails, Boolean isPatchRequest) throws ResourceNotFoundException, ValidationException {
        updateDto.setUserId(authDetails.getUserId());
        return super.update(id, updateDto, authDetails, isPatchRequest);
    }
}
