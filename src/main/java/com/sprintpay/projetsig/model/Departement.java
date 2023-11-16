package com.sprintpay.projetsig.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

import java.util.Collection;


@Entity
@Table(name = "departement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private Integer id;
    private Long id_0;
    private String iso;
    private String name_0;
    private Long id_1;
    private String name_1;
    private Long id_2;
    private String name_2;
    private String hasch_2;
    private Long ccn_2;
    private String cca_2;
    private String type_2;
    private String engtype_2;
    private String nl_name_2;
    private String varname_2;
    @JsonIgnore
    private Geometry geom;
    @ManyToOne
    @JsonIgnore
    private Region region;
    @JsonIgnore
    @OneToMany(mappedBy = "departement", fetch = FetchType.LAZY)
    private Collection<Arrondissement> arrondissement;
}
