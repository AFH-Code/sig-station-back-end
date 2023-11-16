package com.sprintpay.projetsig.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "region")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private Integer id;
    private Long id_0;
    private String iso;
    private String name_0;
    private Long id_1;
    private String name_1;
    private String hasch_1;
    private Long ccn_1;
    private String cca_1;
    private String type_1;
    private String engtype_1;
    private String nl_name_1;
    private String varname_1;
    @JsonIgnore
    private Geometry geom;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Collection<Departement> departement;

}
