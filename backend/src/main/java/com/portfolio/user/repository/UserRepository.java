package com.portfolio.user.repository;

import com.portfolio.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findUserByEmail(String email);

//    Page<User> findAllByDeletedFalse(@NotNull Pageable pageable);

//    Page<User> findAllByDeletedTrue(Pageable pageable);
}
