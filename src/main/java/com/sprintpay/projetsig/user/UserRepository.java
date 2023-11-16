package com.sprintpay.projetsig.user;

import com.sprintpay.projetsig.model.PrincipalStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE (:x is null OR LOWER(u.firstname) LIKE CONCAT('%', LOWER(:x), '%')) AND (:x is null OR LOWER(u.lastname) LIKE CONCAT('%', LOWER(:x), '%'))")
    public Page<User> findAll(
            @Param("x") String search,
            Pageable pageable
    );
}
