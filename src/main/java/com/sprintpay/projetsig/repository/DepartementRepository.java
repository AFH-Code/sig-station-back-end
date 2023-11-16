package com.sprintpay.projetsig.repository;

import com.sprintpay.projetsig.model.Departement;
import com.sprintpay.projetsig.model.PrincipalStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.lang.String;
@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer> {
    @Query("select d from Departement d where d.name_2 like :x")
    public List<Departement> findDepartementSeach(
            @Param("x")String search
    );

}
