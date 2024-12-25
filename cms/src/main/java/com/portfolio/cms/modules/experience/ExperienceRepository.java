package com.portfolio.cms.modules.experience;

import com.portfolio.cms.modules.experience.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExperienceRepository extends JpaRepository<Experience, Integer>, JpaSpecificationExecutor<Experience> {
}
