package com.sprintpay.projetsig.repository;

import com.sprintpay.projetsig.model.EarthStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarthStationRepository extends JpaRepository<EarthStation, Integer> {
}
