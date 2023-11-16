package com.sprintpay.projetsig.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sprintpay.projetsig.model.PrincipalStation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class AntennaDTO {
    private String antennaName;
    private String azimutRadiation;
    private String elevation;
    private String antennaHeigth;
    private String antennaClass;
    private String polarization;
    private String antennaGain;
    private String antennaDirectivity;
    private String horBeamWidth;
    private String rotAzimut;
    private String antennaType;
    private String antennaGainType;
    private String antennaSize;
    private String verBeamWidth;
    private String referenceAntenna;
    private String freqRangeFrom;
    private String freqRangeTo;
    private String crossPolarDisc;
    private String insertionLoss;

    @ManyToOne
    @JsonIgnore
    private PrincipalStation principal_station;
}
