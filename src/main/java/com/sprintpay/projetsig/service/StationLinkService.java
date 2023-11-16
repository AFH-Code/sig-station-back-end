package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.dto.OperatorDTO;
import com.sprintpay.projetsig.dto.StationLinkDTO;
import com.sprintpay.projetsig.exceptions.OperatorNoFoundException;
import com.sprintpay.projetsig.exceptions.PrincipalStationNoFoundException;
import com.sprintpay.projetsig.model.Operator;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.model.StationLink;
import com.sprintpay.projetsig.repository.StationLinkRepository;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StationLinkService {

    private StationLinkRepository stationLinkRepository;

    public StationLinkService(StationLinkRepository stationLinkRepository) {
        this.stationLinkRepository = stationLinkRepository;
    }

    public List<StationLink> updateStationLinks(){
        List<StationLink> stationLinks = this.stationLinkRepository.findAll();
        List<StationLink> stationLinksUpdates = new ArrayList<>();
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        for (StationLink current : stationLinks) {
            Coordinate coordinate1 = new Coordinate(current.getLongFirstStation(), current.getLatFirstStation());
            Coordinate coordinate2 = new Coordinate(current.getLongSecondStation(), current.getLatSecondStation());

            // Create a LineString object using the two points
            LineString lineString = geometryFactory.createLineString(new Coordinate[]{coordinate1, coordinate2});

            // Return the LineString object
            current.setGeom(lineString);
            System.out.println(current.getId());
            stationLinksUpdates.add(current);
        }
        stationLinkRepository.saveAllAndFlush(stationLinksUpdates);
        return stationLinks;
    }

    public StationLink saveStationLink(StationLinkDTO stationLinkDTO){
        StationLink stationLink = new StationLink();
        stationLink.setLinkName(stationLinkDTO.getLinkName());
        stationLink.setFieldName(stationLinkDTO.getFieldName());
        stationLink.setLatFirstStation(stationLinkDTO.getLatFirstStation());
        stationLink.setLongFirstStation(stationLinkDTO.getLongFirstStation());
        stationLink.setLatSecondStation(stationLinkDTO.getLatSecondStation());
        stationLink.setLongSecondStation(stationLinkDTO.getLongSecondStation());

        return this.stationLinkRepository.save(stationLink);
    }


    public List<StationLink> getAllStationLink(){
        return this.stationLinkRepository.findAll();
    }

    public Optional<StationLink> getOneStationLink(Integer id){
        Optional<StationLink> stationLink = this.stationLinkRepository.findById(id);
        if(!stationLink.isPresent()){
            throw new OperatorNoFoundException(String.format("operator with id %s not found!" + id));
        }
        return this.stationLinkRepository.findById(id);
    }

   /* public Operator updateOperator(Operator principalStation, Integer id){
        Optional<Operator> operatorExist = this.operatorRepository.findById(id);
        if(!operatorExist.isPresent()){
            throw new OperatorNoFoundException(String.format("operator with id %s not found!" + id));
        }
        return this.operatorRepository.save(principalStation);
    }

    public void removeOperator(Integer id){
        Optional<Operator> operator = this.operatorRepository.findById(id);
        if(!operator.isPresent()){
            throw new OperatorNoFoundException(String.format("operator with id %s not found!" + id));
        }
        this.operatorRepository.delete(operator.get());
    }*/

}
