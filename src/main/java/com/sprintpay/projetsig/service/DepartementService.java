package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.exceptions.DepartementNoFoundException;
import com.sprintpay.projetsig.exceptions.RegionNoFoundException;
import com.sprintpay.projetsig.model.Departement;
import com.sprintpay.projetsig.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    private DepartementRepository departementRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartementService(DepartementRepository departementRepository, JdbcTemplate jdbcTemplate){
        this.departementRepository = departementRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Departement saveDepartement(Departement departement){

        return this.departementRepository.save(departement);
    }

    public String convertToGeoJson(Integer id) {
        String sql = "SELECT ST_AsGeoJSON(ST_Transform(geom, 4326)) FROM departement WHERE gid = " + id;
        String geoJSON = jdbcTemplate.queryForObject(sql, String.class);

        if (geoJSON == null) {
            throw new DepartementNoFoundException(String.format("Region with id %s not found!", id));
        }

        return geoJSON;
    }

    public List<Departement> getAllDepartement(){
        return this.departementRepository.findAll();
    }
    public List<Departement> getAllDepartements(String search){
        return this.departementRepository.findDepartementSeach(search);
    }

    public Optional<Departement> getOneDepartement(Integer id){
        Optional<Departement> departement = this.departementRepository.findById(id);
        if(!departement.isPresent()){
            throw new DepartementNoFoundException(String.format("Departement with id %s not found!" + id));
        }
        return this.departementRepository.findById(id);
    }

    public Departement updateDepartement(Departement departement,Integer id){
        Optional<Departement> departementExist = this.departementRepository.findById(id);
        if(!departementExist.isPresent()){
            throw new DepartementNoFoundException(String.format("Departement with id %s not found!" + id));
        }
        return this.departementRepository.save(departement);
    }

    public void removeDepartement(Integer id){
        Optional<Departement> departement = this.departementRepository.findById(id);
        if(!departement.isPresent()){
            throw new DepartementNoFoundException(String.format("Departement with id %s not found!" + id));
        }
        this.departementRepository.delete(departement.get());
    }
}
