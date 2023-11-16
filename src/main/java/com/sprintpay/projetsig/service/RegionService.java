package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.exceptions.PrincipalStationNoFoundException;
import com.sprintpay.projetsig.exceptions.RegionNoFoundException;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.model.Region;
import com.sprintpay.projetsig.repository.DepartementRepository;
import com.sprintpay.projetsig.repository.PrincipalStationRepository;
import com.sprintpay.projetsig.repository.RegionRepository;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    private  JdbcTemplate jdbcTemplate;
    private RegionRepository regionRepository;
    private PrincipalStationRepository principalStationRepository;
    private DepartementRepository departementRepository;

    @Autowired
    public RegionService(JdbcTemplate jdbcTemplate, RegionRepository regionRepository, PrincipalStationRepository principalStationRepository, DepartementRepository departementRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.regionRepository = regionRepository;
        this.principalStationRepository = principalStationRepository;
        this.departementRepository = departementRepository;
    }

    public String convertToGeoJson(Integer id) {
        String sql = "SELECT ST_AsGeoJSON(ST_Transform(geom, 4326)) FROM region WHERE gid = " + id;
        String geoJSON = jdbcTemplate.queryForObject(sql, String.class);

        if (geoJSON == null) {
            throw new RegionNoFoundException(String.format("Region with id %s not found!", id));
        }

        return geoJSON;
    }


    public Region saveRegion(Region region){

        return this.regionRepository.save(region);
    }

    public List<Region> getAllRegion(){
        return this.regionRepository.findAll();
    }

    public List<Region> getAllRegions(){
        List departements = this.departementRepository.findAll();
        return this.regionRepository.findAll();
    }


    public Optional<Region> getOneRegion(Integer id){
        Optional<Region> region = this.regionRepository.findById(id);
        if(!region.isPresent()){
            throw new RegionNoFoundException(String.format("Region with id %s not found!" + id));
        }
        return this.regionRepository.findById(id);
    }

    public Region updateRegion(Region region,Integer id){
        Optional<Region> regionExist = this.regionRepository.findById(id);
        if(!regionExist.isPresent()){
            throw new RegionNoFoundException(String.format("Region with id %s not found!" + id));
        }
        return this.regionRepository.save(region);
    }

    public void removeRegion(Integer id){
        Optional<Region> region = this.regionRepository.findById(id);
        if(!region.isPresent()){
            throw new RegionNoFoundException(String.format("Region with id %s not found!" + id));
        }
        this.regionRepository.delete(region.get());
    }
}
