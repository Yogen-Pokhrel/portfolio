package com.portfolio.account.user.repository;

import com.portfolio.account.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByEmailIgnoreCaseAndIdNot(String email, Integer id);

    Optional<User> findUserByEmailIgnoreCase(String email);

//    Page<User> findAllByDeletedFalse(@NotNull Pageable pageable);

//    Page<User> findAllByDeletedTrue(Pageable pageable);
}
