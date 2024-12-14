package com.portfolio.account.role;

import com.portfolio.account.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findBySlug(String slug);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.users WHERE r.slug = :slug")
    Optional<Role> findBySlugWithUsers(@Param("slug") String slug);
}
