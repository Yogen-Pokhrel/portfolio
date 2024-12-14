package com.portfolio.auth.systemModule;

import com.portfolio.core.service.SimpleCrudService;
import com.portfolio.core.exception.ValidationException;
import com.portfolio.auth.systemModule.dto.request.SystemModuleCreateDto;
import com.portfolio.auth.systemModule.dto.request.SystemModuleUpdateDto;
import com.portfolio.auth.systemModule.dto.response.SystemModuleResponseDto;
import com.portfolio.auth.systemModule.entity.SystemModule;
import com.portfolio.auth.systemModule.repository.SystemModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class SystemModuleService extends SimpleCrudService<SystemModuleRepository, SystemModule, SystemModuleCreateDto, SystemModuleUpdateDto, SystemModuleResponseDto, Integer> {

    SystemModuleRepository systemModuleRepository;

    @Autowired
    public SystemModuleService(SystemModuleRepository systemModuleRepository, ModelMapper modelMapper){
        super(systemModuleRepository, SystemModule.class, SystemModuleResponseDto.class);
        this.systemModuleRepository = systemModuleRepository;
    }

    @Transactional
    protected void validate(SystemModule systemModule) throws ValidationException {
        if(systemModule.getSlug() != null){
            Optional<SystemModule> existingRecord;
            if(systemModule.getId() != null){
                existingRecord = this.systemModuleRepository.findBySlugIgnoreCaseAndIdNot(systemModule.getSlug(), systemModule.getId());
            }else{
                existingRecord = this.systemModuleRepository.findBySlugIgnoreCase(systemModule.getSlug());
            }
            if(existingRecord.isPresent()){
                throw new ValidationException("Slug already exists");
            }
        }
        super.validate(systemModule);
    }
}
