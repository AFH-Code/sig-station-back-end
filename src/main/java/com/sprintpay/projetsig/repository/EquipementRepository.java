package com.sprintpay.projetsig.repository;

import com.sprintpay.projetsig.model.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Integer> {
}
