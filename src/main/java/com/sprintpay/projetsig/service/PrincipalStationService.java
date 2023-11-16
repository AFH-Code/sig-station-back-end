package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.dto.PrincipalStationDTO;
import com.sprintpay.projetsig.dto.PrincipalStationUpdateDTO;
import com.sprintpay.projetsig.exceptions.AntennaNoFoundException;
import com.sprintpay.projetsig.exceptions.ArrondissementNoFoundException;
import com.sprintpay.projetsig.exceptions.OperatorNoFoundException;
import com.sprintpay.projetsig.exceptions.PrincipalStationNoFoundException;
import com.sprintpay.projetsig.model.*;
import com.sprintpay.projetsig.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.locationtech.jts.geom.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Component
public class PrincipalStationService {

    @Autowired
    private HttpServletResponse response;
    private PrincipalStationRepository principalStationRepository;
    private ArrondissementRepository arrondissementRepository;
    private RegionRepository regionRepository;
    private OperatorRepository operatorRepository;
    private AntennaRepository antennaRepository;
    private final ObjectMapper objectMapper;



    @Autowired
    public PrincipalStationService(PrincipalStationRepository principalStationRepository, ArrondissementRepository arrondissementRepository, RegionRepository regionRepository, OperatorRepository operatorRepository, AntennaRepository antennaRepository, ObjectMapper objectMapper){
        this.principalStationRepository = principalStationRepository;
        this.arrondissementRepository = arrondissementRepository;
        this.regionRepository = regionRepository;
        this.operatorRepository = operatorRepository;
        this.antennaRepository = antennaRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initializeViews() {

      principalStationRepository.executeSql("CREATE OR REPLACE VIEW stations_mtnc AS\n" +
                "SELECT p.id, p.assigned_freq, p.band_width, p.channel_separation, p.field_name, p.freq_range, p.heightasl, p.is_validate, p.link_name,  p.radius_of_service, p.response_freq, p.station_name, p.type, p.type_station, p.operator_adress, p.operator_name, p.arrondissement_gid, p.geom\n" +
                "FROM principal_station AS p\n" +
                "WHERE p.operator_name = 'MTNC' AND p.is_validate = true;");


        principalStationRepository.executeSql("CREATE OR REPLACE VIEW stations_ihs AS\n" +
                "SELECT p.id, p.assigned_freq, p.band_width, p.channel_separation, p.field_name, p.freq_range, p.heightasl, p.is_validate, p.link_name,  p.radius_of_service, p.response_freq, p.station_name, p.type, p.type_station, p.operator_adress, p.operator_name, p.arrondissement_gid, p.geom\n" +
                "FROM principal_station AS p\n" +
                "WHERE p.operator_name = 'IHS' AND p.is_validate = true;");


        principalStationRepository.executeSql("CREATE OR REPLACE VIEW stations_orange AS\n" +
                "SELECT p.id, p.assigned_freq, p.band_width, p.channel_separation, p.field_name, p.freq_range, p.heightasl, p.is_validate, p.link_name,  p.radius_of_service, p.response_freq, p.station_name, p.type, p.type_station, p.operator_adress, p.operator_name, p.arrondissement_gid, p.geom\n" +
                "FROM principal_station AS p\n" +
                "WHERE p.operator_name = 'ORANGE' AND p.is_validate = true;");


        principalStationRepository.executeSql("CREATE OR REPLACE VIEW stations_camtel AS\n" +
                "SELECT p.id, p.assigned_freq, p.band_width, p.channel_separation, p.field_name, p.freq_range, p.heightasl, p.is_validate, p.link_name,  p.radius_of_service, p.response_freq, p.station_name, p.type, p.type_station, p.operator_adress, p.operator_name, p.arrondissement_gid, p.geom\n" +
                "FROM principal_station AS p\n" +
                "WHERE p.operator_name = 'CAMTEL' AND p.is_validate = true;");

    }



   /* public String convertToGeoJson(Integer id) {
        Optional<Region> region = this.regionRepository.findById(id);
        if (!region.isPresent()) {
            throw new PrincipalStationNoFoundException(String.format("PrincipalStaion with id %s not found!", id));
        }

        Geometry geom = region.get().getGeom();
        CustomPolygon geometry;

        if (geom instanceof MultiPolygon) {
            MultiPolygon multiPolygon = (MultiPolygon) geom;
            Polygon[] polygons = new Polygon[multiPolygon.getNumGeometries()];

            for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
                polygons[i] = (Polygon) multiPolygon.getGeometryN(i);
            }

            geometry = new CustomPolygon(polygons, geom.getSRID());
        } else if (geom instanceof CustomPolygon) {
            geometry = (CustomPolygon) geom;
        } else {
            throw new IllegalArgumentException("Unsupported geometry type: " + geom.getClass().getSimpleName());
        }

        try {
            // Convertissez l'objet Geometry en une représentation JSON
            String geometryJSON = objectMapper.writeValueAsString(GeoJson.geom(geometry));

            // Obtenez l'enveloppe rectangulaire de la géométrie du polygone
            Envelope envelope = geometry.getEnvelopeInternal();

            // Construisez le GeoJSON en utilisant la représentation JSON de l'objet Geometry et l'enveloppe rectangulaire
            String geoJSON = "{\"type\": \"Feature\", \"geometry\": " + geometryJSON + ", \"bbox\": [" + envelope.getMinX() + ", " + envelope.getMinY() + ", " + envelope.getMaxX() + ", " + envelope.getMaxY() + "]}";

            return geoJSON;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // ou lancez une exception appropriée
        }
    }*/



    public PrincipalStation savePrincipalStation(PrincipalStationDTO principalStationDTO) {
        PrincipalStation principalStation = new PrincipalStation();
        principalStation.setLongitude(principalStationDTO.getLongitude());
        principalStation.setLatitude(principalStationDTO.getLatitude());

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Geometry point = geometryFactory.createPoint(new Coordinate(principalStationDTO.getLongitude(), principalStationDTO.getLatitude()));
        principalStation.setAssignedFreq(principalStationDTO.getAssignedFreq());
        principalStation.setBandWidth(principalStationDTO.getBandWidth());
        principalStation.setChannelSeparation(principalStationDTO.getChannelSeparation());
        principalStation.setFieldName(principalStationDTO.getFieldName());
        principalStation.setLinkName(principalStationDTO.getLinkName());
        principalStation.setFreqRange(principalStationDTO.getFreqRange());
        principalStation.setHeightASL(principalStationDTO.getHeightASL());
        principalStation.setValidate(false);
        principalStation.setLatitude(principalStationDTO.getLatitude());
        principalStation.setLongitude(principalStationDTO.getLongitude());
        principalStation.setOperatorAdress(principalStationDTO.getOperatorAdress());
        principalStation.setOperatorName(principalStationDTO.getOperatorName());
        principalStation.setRadiusOfService(principalStationDTO.getRadiusOfService());
        principalStation.setResponseFreq(principalStationDTO.getResponseFreq());
        principalStation.setStationName(principalStationDTO.getStationName());
        principalStation.setType(principalStationDTO.getType());
        principalStation.setTypeStation(principalStationDTO.getTypeStation());
        principalStation.setGeom(point);

        return this.principalStationRepository.save(principalStation);
    }


    public PrincipalStation addPrincipalStationWithArrondissementAndOperator(PrincipalStationDTO principalStationDTO, Integer arrondissementId, Integer operatorId) {
        PrincipalStation principalStation = new PrincipalStation();
        principalStation.setLongitude(principalStationDTO.getLongitude());
        principalStation.setLatitude(principalStationDTO.getLatitude());

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        Geometry point = geometryFactory.createPoint(new Coordinate(principalStationDTO.getLongitude(), principalStationDTO.getLatitude()));

        Optional<Arrondissement> arrondissementOptional = this.arrondissementRepository.findById(arrondissementId);

        if (!arrondissementOptional.isPresent()) {
            throw new ArrondissementNoFoundException(String.format("Arrondissement with id %s not found!", arrondissementId));
        }
        Optional<Operator> operatorOptional = this.operatorRepository.findById(operatorId);

        if (!arrondissementOptional.isPresent()) {
            throw new OperatorNoFoundException(String.format("Operator with id %s not found!", operatorId));
        }
        Arrondissement arrondissement = arrondissementOptional.get();
        Operator operator = operatorOptional.get();
        principalStation.setAssignedFreq(principalStationDTO.getAssignedFreq());
        principalStation.setBandWidth(principalStationDTO.getBandWidth());
        principalStation.setChannelSeparation(principalStationDTO.getChannelSeparation());
        principalStation.setFieldName(principalStationDTO.getFieldName());
        principalStation.setLinkName(principalStationDTO.getLinkName());
        principalStation.setFreqRange(principalStationDTO.getFreqRange());
        principalStation.setHeightASL(principalStationDTO.getHeightASL());
        principalStation.setValidate(false);
        principalStation.setLatitude(principalStationDTO.getLatitude());
        principalStation.setLongitude(principalStationDTO.getLongitude());
        principalStation.setOperatorAdress(principalStationDTO.getOperatorAdress());
        principalStation.setOperatorName(principalStationDTO.getOperatorName());
        principalStation.setRadiusOfService(principalStationDTO.getRadiusOfService());
        principalStation.setResponseFreq(principalStationDTO.getResponseFreq());
        principalStation.setStationName(principalStationDTO.getStationName());
        principalStation.setType(principalStationDTO.getType());
        principalStation.setTypeStation(principalStationDTO.getTypeStation());
        principalStation.setArrondissement(arrondissement);
        principalStation.setOperator(operator);
        principalStation.setGeom(point);

        return this.principalStationRepository.save(principalStation);
    }
    public PrincipalStation addArrondissementToPrincipalStation(Integer idArrond, Integer idStation){

        Optional<Arrondissement> arrondissement = this.arrondissementRepository.findById(idArrond);

        if(!arrondissement.isPresent()){
            throw new ArrondissementNoFoundException(String.format("Arrondissement with id %s not found!",idArrond));
        }

        Optional<PrincipalStation> principalStation = this.principalStationRepository.findById(idStation);

       if(!principalStation.isPresent()){
            throw new PrincipalStationNoFoundException(String.format("PrincipalStaion with id %s not found!",idStation));
        }
        principalStation.get().setArrondissement(arrondissement.get());

        return this.principalStationRepository.save(principalStation.get());
    }

    public PrincipalStation addAntennaToPrincipalStation(Integer idArrond, Integer idStation){

        Optional<Antenna> antenna = this.antennaRepository.findById(idArrond);

        if(!antenna.isPresent()){
            throw new AntennaNoFoundException(String.format("Antenna with id %s not found!",idArrond));
        }

        Optional<PrincipalStation> principalStation = this.principalStationRepository.findById(idStation);

        if(!principalStation.isPresent()){
            throw new PrincipalStationNoFoundException(String.format("PrincipalStaion with id %s not found!",idStation));
        }

        return this.principalStationRepository.save(principalStation.get());
    }


    public Page<PrincipalStation> getAllPrincipalStation(Pageable pageable){
        return this.principalStationRepository.findAll(pageable);
    }

    public Page<PrincipalStation> getAllPrincipalStations(List<TypeStationP> type, Pageable pageable){

        return this.principalStationRepository.findByType(type==null? null:type.isEmpty()? null:type, pageable);
    }

   /* public Page<PrincipalStation> getAllPrincipalStationByType(Integer idOperator,List<TypeStationP> type, String search, Pageable pageable){
        Optional<Operator> operatorExist = this.operatorRepository.findById(idOperator);
        String operatorName = operatorExist.get().getOwnerName();
        if(!operatorExist.isPresent()){
            throw new OperatorNoFoundException(String.format("operator with id %s not found!" + idOperator));
        }
        if (idOperator == null) {
            return this.principalStationRepository.findPrincipalStationSeachByTyp(type==null? null:type.isEmpty()? null:type, search==null?null:search, pageable);
        } else if (idOperator != null && operatorExist.get().getPrincipalStation()==null?null:operatorExist.get().getPrincipalStation().isEmpty()){
            return this.principalStationRepository.findByOperatorName(type==null? null:type.isEmpty()? null:type, search==null?null:search, operatorName==null?null:operatorName, pageable);
        }else {
            return this.principalStationRepository.findPrincipalStationSeachByType(idOperator, type==null? null:type.isEmpty()? null:type, search==null?null:search, pageable);
        }
    }*/
    public Page<PrincipalStation> getAllPrincipalStationByType(Integer idOperator, List<TypeStationP> type, String search, Pageable pageable) {
        if (idOperator == null) {
            return this.principalStationRepository.findPrincipalStationSeachByTyp(type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, pageable);
        } else {
            Optional<Operator> operatorExist = this.operatorRepository.findById(idOperator);
            if (!operatorExist.isPresent()) {
                throw new OperatorNoFoundException(String.format("operator with id %s not found!", idOperator));
            }
            String operatorName = operatorExist.get().getOwnerName();
            //if (operatorExist.get().getPrincipalStation() == null ? null : operatorExist.get().getPrincipalStation().isEmpty()) {
                return this.principalStationRepository.findByOperatorName(type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, operatorName == null ? null : operatorName, pageable);
            //} else {
                //return this.principalStationRepository.findPrincipalStationSeachByType(idOperator, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search,operatorName == null ? null : operatorName, pageable);
            //}
        }
    }

    public Page<PrincipalStation> getAllPrincipalStationOfOperator( Integer id, String search, Pageable pageable){
        return this.principalStationRepository.findPrincipalStationSeachByIdOperator(id,  search==null?null:search, pageable);
    }
    /*public Page<PrincipalStation> getAllPrincipalStationOfArrandissement( Integer id, List<TypeStationP> type, String search, Pageable pageable){
        return this.principalStationRepository.findPrincipalStationSeachByIdArrondissemnt(id, type==null? null:type.isEmpty()? null:type, search==null?null:search, pageable);
    }*/
    public Page<PrincipalStation> getAllPrincipalStationOfArrandissementAndOperator(Integer idArrondissement, Integer idOperator, List<TypeStationP> type, String search, Pageable pageable) {
        if (idOperator == null) {
            // Gérer le cas où idOperator est nulle
            return this.principalStationRepository.findPrincipalStationSeachByIdArrondissemnt(idArrondissement, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, pageable);
        }else {
            Optional<Operator> operatorExist = this.operatorRepository.findById(idOperator);
            if (!operatorExist.isPresent()) {
                throw new OperatorNoFoundException(String.format("operator with id %s not found!", idOperator));
            }
            String operatorName = operatorExist.get().getOwnerName();
            //if (operatorExist.get().getPrincipalStation() == null ? null : operatorExist.get().getPrincipalStation().isEmpty()) {
                return this.principalStationRepository.findByOperatorNameAndIdArrondissemnt(idArrondissement, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, operatorName == null ? null : operatorName, pageable);
            //} else {
                //return this.principalStationRepository.findPrincipalStationSearchByIdArrondissemntAndOperator(idArrondissement, idOperator, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, pageable);
            //}
        }
    }


    /*public Page<PrincipalStation> getAllPrincipalStationOfDepartement( Integer id, List<TypeStationP> type, String search,  Pageable pageable){
        return this.principalStationRepository.findPrincipalStationSeachByIdDepartement(id, type==null? null:type.isEmpty()? null:type, search==null?null:search, pageable);
    }*/
    public Page<PrincipalStation> getAllPrincipalStationOfDepartementAndOperator(Integer idDepartement, Integer idOperator, List<TypeStationP> type, String search, Pageable pageable) {
        if (idOperator == null) {
            // Gérer le cas où idOperator est nulle
            return this.principalStationRepository.findPrincipalStationSeachByIdDepartement(idDepartement, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, pageable);
        }else {
            Optional<Operator> operatorExist = this.operatorRepository.findById(idOperator);
            if (!operatorExist.isPresent()) {
                throw new OperatorNoFoundException(String.format("operator with id %s not found!", idOperator));
            }
            String operatorName = operatorExist.get().getOwnerName();
            //if (operatorExist.get().getPrincipalStation() == null ? null : operatorExist.get().getPrincipalStation().isEmpty()) {
                return this.principalStationRepository.findPrincipalStationSeachByIdDepartementOperatorName(idDepartement, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, operatorName == null ? null : operatorName, pageable);
            //} else {
                //return this.principalStationRepository.findPrincipalStationSearchByIdDepartementAndOperator(idDepartement, idOperator, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, pageable);
            //}
        }

    }

    /*public Page<PrincipalStation> getAllPrincipalStationOfRegion( Integer id, List<TypeStationP> type, String search,  Pageable pageable){
        return this.principalStationRepository.findPrincipalStationSeachByIdRegion(id, type==null? null:type.isEmpty()? null:type, search==null?null:search, pageable);
    }*/
    public Page<PrincipalStation> getAllPrincipalStationOfRegionAndOperator(Integer idRegion, Integer idOperator, List<TypeStationP> type, String search, Pageable pageable) {
        if (idOperator == null) {
            // Gérer le cas où idOperator est nulle
            return this.principalStationRepository.findPrincipalStationSeachByIdRegion(idRegion, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, pageable);
        }else {
            Optional<Operator> operatorExist = this.operatorRepository.findById(idOperator);
            if (!operatorExist.isPresent()) {
                throw new OperatorNoFoundException(String.format("operator with id %s not found!", idOperator));
            }
            String operatorName = operatorExist.get().getOwnerName();
            //if (operatorExist.get().getPrincipalStation() == null ? null : operatorExist.get().getPrincipalStation().isEmpty()) {
                return this.principalStationRepository.findPrincipalStationSeachByIdRegionOperatorName(idRegion, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, operatorName == null ? null : operatorName, pageable);
            //} else {
                //return this.principalStationRepository.findPrincipalStationSearchByIdRegionAndOperator(idRegion, idOperator, type == null ? null : type.isEmpty() ? null : type, search == null ? null : search, pageable);
            //}
        }
    }

    public Optional<PrincipalStation> getOnePrincipalStation(Integer id){

        Optional<PrincipalStation> principalStation = this.principalStationRepository.findById(id);
        if(!principalStation.isPresent()){
            throw new PrincipalStationNoFoundException(String.format("PrincipalStation with id %s not found!",id));
        }
        return this.principalStationRepository.findById(id);
    }

    public List<PrincipalStation> updatePrincipalStations(){
        List<PrincipalStation> principalStations = this.principalStationRepository.findAll();
        List<PrincipalStation> principalStationsUpdates = new ArrayList<>();
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        for (PrincipalStation current : principalStations) {
            Geometry point = geometryFactory.createPoint(new Coordinate(current.getLongitude(), current.getLatitude()));
            current.setGeom(point);
            System.out.println(current.getId());
            principalStationsUpdates.add(current);
        }
        principalStationRepository.saveAllAndFlush(principalStationsUpdates);
        return principalStations;
    }

    public PrincipalStation updatePrincipalStation(PrincipalStationUpdateDTO principalStation, Integer id){
        Optional<PrincipalStation> principalStationExist = this.principalStationRepository.findById(id);
        if(!principalStationExist.isPresent()){
            throw new PrincipalStationNoFoundException(String.format("PrincipalStation with id %s not found!",id));
        }
        Optional<Arrondissement> arrondissementExist = this.arrondissementRepository.findById(principalStation.getArrondissement().getId());
        if(!arrondissementExist.isPresent()){
            throw new ArrondissementNoFoundException(String.format("Arrondissement with id %s not found!",id));
        }
        Optional<Operator> operatorExist = this.operatorRepository.findById(principalStation.getOperator().getId());
        if(!arrondissementExist.isPresent()){
            throw new ArrondissementNoFoundException(String.format("Arrondissement with id %s not found!",id));
        }
        PrincipalStation existingStation = principalStationExist.get();
        existingStation.setAssignedFreq(principalStation.getAssignedFreq());
        existingStation.setBandWidth(principalStation.getBandWidth());
        existingStation.setChannelSeparation(principalStation.getChannelSeparation());
        existingStation.setStationName(principalStation.getStationName());
        existingStation.setFieldName(principalStation.getFieldName());
        existingStation.setFreqRange(principalStation.getFreqRange());
        existingStation.setHeightASL(principalStation.getHeightASL());
        existingStation.setValidate(principalStation.isValidate());
        existingStation.setLatitude(principalStation.getLatitude());
        existingStation.setLinkName(principalStation.getLinkName());
        existingStation.setLongitude(principalStation.getLongitude());
        existingStation.setRadiusOfService(principalStation.getRadiusOfService());
        existingStation.setResponseFreq(principalStation.getResponseFreq());
        existingStation.setStationName(principalStation.getStationName());
        existingStation.setTypeStation(principalStation.getTypeStation());
        existingStation.setType(principalStation.getType());
        existingStation.setOperatorAdress(principalStation.getOperatorAdress());
        existingStation.setOperatorName(principalStation.getOperatorName());
        existingStation.setArrondissement(arrondissementExist.get());
        existingStation.setOperator(operatorExist.get());

        return this.principalStationRepository.save(existingStation);
    }
    public PrincipalStation validate(Integer id) {
        Optional<PrincipalStation> principalStationExist = this.principalStationRepository.findById(id);
        if (!principalStationExist.isPresent()) {
            throw new PrincipalStationNoFoundException(String.format("PrincipalStation with id %s not found!", id));
        }
        PrincipalStation existingStation = principalStationExist.get();
        existingStation.setValidate(!existingStation.isValidate());
        return this.principalStationRepository.save(existingStation);
    }



   /* public PrincipalStation updatePrincipalStation(PrincipalStationUpdateDTO principalStation, Integer id){
        Optional<PrincipalStation> principalStationExist = this.principalStationRepository.findById(id);
        if(!principalStationExist.isPresent()){
            throw new PrincipalStationNoFoundException(String.format("PrincipalStation with id %s not found!",id));
        }
        Optional<Arrondissement> arrondissementExist = this.arrondissementRepository.findById(principalStation.getArrondissement().getId());
        if(!arrondissementExist.isPresent()){
            throw new ArrondissementNoFoundException(String.format("Arrondissement with id %s not found!",id));
        }
        PrincipalStation existingStation = principalStationExist.get();
        existingStation.setAssignedFreq(principalStation.getAssignedFreq());
        existingStation.setBandWidth(principalStation.getBandWidth());
        existingStation.setChannelSeparation(principalStation.getChannelSeparation());
        existingStation.setStationName(principalStation.getStationName());
        existingStation.setFieldName(principalStation.getFieldName());
        existingStation.setFreqRange(principalStation.getFreqRange());
        existingStation.setHeightASL(principalStation.getHeightASL());
        existingStation.setLatitude(principalStation.getLatitude());
        existingStation.setLinkName(principalStation.getLinkName());
        existingStation.setLongitude(principalStation.getLongitude());
        existingStation.setRadiusOfService(principalStation.getRadiusOfService());
        existingStation.setResponseFreq(principalStation.getResponseFreq());
        existingStation.setStationName(principalStation.getStationName());
        existingStation.setTypeStation(principalStation.getTypeStation());
        existingStation.setType(principalStation.getType());
        existingStation.setOperatorAdress(principalStation.getOperatorAdress());
        existingStation.setOperatorName(principalStation.getOperatorName());

        existingStation.setArrondissement(arrondissementExist.get());

        return this.principalStationRepository.save(existingStation);
    }*/

    public void removePrincipalStation(Integer id) {
        Optional<PrincipalStation> principalStation = this.principalStationRepository.findById(id);
        /*if (!principalStation.isPresent()) {
            throw new PrincipalStationNoFoundException(String.format("PrincipalStation with id %s not found!", id));
        }*/
        // Récupérer toutes les entités "Antenna" associées à la "PrincipalStation"
        //List<Antenna> antennas = principalStation.get().getAntennas();
        // Supprimer les entités "Antenna"
        //antennas.forEach(antenna -> antennaRepository.delete(antenna));
        this.principalStationRepository.delete(principalStation.get());
    }

}
