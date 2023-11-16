package com.sprintpay.projetsig.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "earth_station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EarthStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String earthStationName;
    private String typical;
    private String associateSpaceStation;
    private int minimumEvalAngle;
    private int azimutAngleFrom;
    private int azimutAngleTo;
    private int altitudeAntenna;
    private double latitude;
    private double longitude;
    private double nominalLongitude;
}
