package com.sprintpay.projetsig.repository;

import com.sprintpay.projetsig.model.Antenna;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AntennaRepository extends JpaRepository<Antenna, Integer> {
    //public Page<Antenna> findAll(Pageable pageable);

    @Query("SELECT a FROM Antenna a WHERE (:x is null OR LOWER(a.antennaName) LIKE CONCAT('%', LOWER(:x), '%'))")
    public Page<Antenna> findAll(
            @Param("x") String search,
            Pageable pageable
    );
    @Query("SELECT a FROM Antenna a JOIN PrincipalStation p ON a.principal_station = p WHERE (p.id = :id) AND (:x is null OR LOWER(a.antennaName) LIKE LOWER('%' || :x || '%'))")
    public Page<Antenna> findAntennaByIdPrincipalStation(
            @Param("id") Integer id,
            @Param("x") String search,
            Pageable pageable
    );
}
