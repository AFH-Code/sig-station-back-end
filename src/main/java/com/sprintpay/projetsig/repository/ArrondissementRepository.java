package com.sprintpay.projetsig.repository;

import com.sprintpay.projetsig.model.Arrondissement;
import com.sprintpay.projetsig.model.Departement;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.model.TypeStationP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ArrondissementRepository extends JpaRepository<Arrondissement, Integer> {

    @Query("select a from Arrondissement a where a.name_3 like :x")
    public List<Arrondissement> findArrondissementSeach(
            @Param("x")String search
    );

    public List<Arrondissement> findArrondissementByDepartementId(Integer id);
}
