package com.portfolio.cms.modules.education;

import com.portfolio.cms.modules.education.dto.EducationCreateUpdateDto;
import com.portfolio.cms.modules.education.dto.EducationResponseDto;
import com.portfolio.cms.modules.education.entity.Education;
import com.portfolio.core.exception.ResourceNotFoundException;
import com.portfolio.core.exception.ValidationException;
import com.portfolio.core.security.AuthDetails;
import com.portfolio.core.service.CrudService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationService extends CrudService<EducationRepository, Education, EducationCreateUpdateDto, EducationResponseDto, Integer> {

    @Autowired
    public EducationService(EducationRepository repository){
        super(repository, Education.class, EducationResponseDto.class);
    }

    @Override
    public EducationResponseDto save(EducationCreateUpdateDto createDto, @NotNull AuthDetails authDetails) throws ValidationException {
        createDto.setUserId(authDetails.getUserId());
        return super.save(createDto, authDetails);
    }

    @Override
    public EducationResponseDto update(Integer id, EducationCreateUpdateDto updateDto, AuthDetails authDetails, Boolean isPatchRequest) throws ResourceNotFoundException, ValidationException {
        updateDto.setUserId(authDetails.getUserId());
        return super.update(id, updateDto, authDetails, isPatchRequest);
    }
}
