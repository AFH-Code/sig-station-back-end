package com.sprintpay.projetsig.repository;

import com.sprintpay.projetsig.model.Region;
import com.sprintpay.projetsig.model.StationLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationLinkRepository extends JpaRepository<StationLink, Integer> {
}
