package com.sprintpay.projetsig.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String linkName;
    private String fieldName;
    private double latFirstStation;
    private double longFirstStation;
    private double latSecondStation;
    private double longSecondStation;
    @JsonIgnore
    private Geometry geom;
}
