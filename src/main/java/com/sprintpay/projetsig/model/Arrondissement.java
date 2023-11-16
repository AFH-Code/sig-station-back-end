package com.sprintpay.projetsig.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import java.util.Collection;


@Entity
@Data
@Table(name = "arrondissement")
@NoArgsConstructor
@AllArgsConstructor
public class Arrondissement {
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
    private Long id_3;
    private String name_3;
    private String hasch_3;
    private Long ccn_3;
    private String cca_3;
    private String type_3;
    private String engtype_3;
    private String nl_name_3;
    private String varname_3;
    @JsonIgnore
    private Geometry geom;
    @ManyToOne
    @JsonIgnore
    private Departement departement;

    @OneToMany(mappedBy = "arrondissement", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<PrincipalStation> principalStation;
}
