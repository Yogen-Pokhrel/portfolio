package com.portfolio.cms.modules.education;

import com.portfolio.cms.modules.education.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer>, JpaSpecificationExecutor<Education> {
}
