package com.sprintpay.projetsig.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sprintpay.projetsig.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "operator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ownerName;
    private String ownerAdress;
    private String billingName;
    private String telephone;
    private String telex;
    private String fax;
    private String email;

    @OneToMany(mappedBy = "operator", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<PrincipalStation> principalStation;

    @OneToMany(mappedBy = "operator", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<User> user;
}
