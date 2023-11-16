package com.sprintpay.projetsig.repository;

import com.sprintpay.projetsig.model.Antenna;
import com.sprintpay.projetsig.model.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer> {

    @Query("SELECT o FROM Operator o WHERE (:x is null OR LOWER(o.ownerName) LIKE CONCAT('%', LOWER(:x), '%'))")
    public Page<Operator> findAll(
            @Param("x") String search,
            Pageable pageable
    );
}
