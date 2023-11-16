package com.sprintpay.projetsig.dto;

import lombok.Data;

@Data
public class StationLinkDTO {
    private String linkName;
    private String fieldName;
    private double latFirstStation;
    private double longFirstStation;
    private double latSecondStation;
    private double longSecondStation;
}
