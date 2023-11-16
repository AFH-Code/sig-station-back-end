package com.sprintpay.projetsig.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "principal_station")
public class PrincipalStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String assignedFreq;
    private String bandWidth;
    private String channelSeparation;
    private String fieldName;
    private String freqRange;
    private String heightASL;
    private boolean isValidate;
    @Column(nullable = true)
    private double latitude;
    private String linkName;
    @Column(nullable = true)
    private double longitude;
    private String radiusOfService;
    private String responseFreq;
    private String stationName;
    private String typeStation;
    @Enumerated(EnumType.STRING)
    private TypeStationP type;
    private String operatorAdress;
    private String operatorName;
    @JsonIgnore
    private Geometry geom;
    @ManyToOne
    private Arrondissement arrondissement;

    @ManyToOne
    private Operator operator;

    @OneToMany(mappedBy = "principal_station", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Antenna> antenna;

    /*public List<Antenna> getAntennas() {
        return new ArrayList<>(antenna);
    }*/
}
