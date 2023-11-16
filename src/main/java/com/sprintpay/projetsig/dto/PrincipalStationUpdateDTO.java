package com.sprintpay.projetsig.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sprintpay.projetsig.model.Arrondissement;
import com.sprintpay.projetsig.model.TypeStationP;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;

@Data
public class PrincipalStationUpdateDTO {
    private String assignedFreq;
    private String bandWidth;
    private String channelSeparation;
    private String fieldName;
    private String freqRange;
    private String heightASL;
    private boolean isValidate;
    private double latitude;
    private String linkName;
    private double longitude;
    private String radiusOfService;
    private String responseFreq;
    private String stationName;
    private String typeStation;
    private TypeStationP type;
    private String operatorAdress;
    private String operatorName;
    private ArrondissementIdDTO arrondissement;
    private OperatorIdDTO operator;

}
