package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.exceptions.ArrondissementNoFoundException;
import com.sprintpay.projetsig.exceptions.RegionNoFoundException;
import com.sprintpay.projetsig.model.Arrondissement;
import com.sprintpay.projetsig.repository.ArrondissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArrondissementService {

   private ArrondissementRepository arrondissementRepository;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ArrondissementService(ArrondissementRepository arrondissementRepository, JdbcTemplate jdbcTemplate){
        this.arrondissementRepository = arrondissementRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public String convertToGeoJson(Integer id) {
        String sql = "SELECT ST_AsGeoJSON(ST_Transform(geom, 4326)) FROM arrondissement WHERE gid = " + id;
        String geoJSON = jdbcTemplate.queryForObject(sql, String.class);

        if (geoJSON == null) {
            throw new ArrondissementNoFoundException(String.format("Arrondissement with id %s not found!", id));
        }

        return geoJSON;
    }

    public Arrondissement saveArrondissement(Arrondissement arrondissement){

        return this.arrondissementRepository.save(arrondissement);
    }

    public List<Arrondissement> getAllArrondissement(){
        return this.arrondissementRepository.findAll();
    }

    public List<Arrondissement> getAllArrondissementByArrondissement(Integer id){
        return this.arrondissementRepository.findArrondissementByDepartementId(id);
    }

    public List<Arrondissement> getAllArrondissements(String search){
        return this.arrondissementRepository.findArrondissementSeach(search);
    }

    public Optional<Arrondissement> getOneArrondissement(Integer id){
        Optional<Arrondissement> arrondissement = this.arrondissementRepository.findById(id);
        if(!arrondissement.isPresent()){
            throw new ArrondissementNoFoundException(String.format("Arrondissement with id %s not found!",id));
        }
        return this.arrondissementRepository.findById(id);
    }


    public Arrondissement updateArrondissement(Arrondissement arrondissement,Integer id){
        Optional<Arrondissement> arrondissementExist = this.arrondissementRepository.findById(id);
        if(!arrondissementExist.isPresent()){
            throw new ArrondissementNoFoundException(String.format("Arrondissement with id %s not found!",id));
        }
        return this.arrondissementRepository.save(arrondissement);
    }

    public void removeArrondissement(Integer id){
        Optional<Arrondissement> arrondissement = this.arrondissementRepository.findById(id);
        if(!arrondissement.isPresent()){
            throw new ArrondissementNoFoundException(String.format("Arrondissement with id %s not found!",id));
        }
        this.arrondissementRepository.delete(arrondissement.get());
    }
}
