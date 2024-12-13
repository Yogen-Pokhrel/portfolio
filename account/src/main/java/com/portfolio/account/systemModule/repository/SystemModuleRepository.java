package com.portfolio.account.systemModule.repository;

import com.portfolio.account.systemModule.entity.SystemModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemModuleRepository extends JpaRepository<SystemModule, Integer> {
    Optional<SystemModule> findBySlugIgnoreCase(String slug);

    Optional<SystemModule> findBySlugIgnoreCaseAndIdNot(String slug, Integer id);
}
