package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.dto.AntennaDTO;
import com.sprintpay.projetsig.dto.AntennaUpdateDTO;
import com.sprintpay.projetsig.exceptions.AntennaNoFoundException;
import com.sprintpay.projetsig.exceptions.ArrondissementNoFoundException;
import com.sprintpay.projetsig.exceptions.PrincipalStationNoFoundException;
import com.sprintpay.projetsig.model.Antenna;
import com.sprintpay.projetsig.model.Arrondissement;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.model.TypeStationP;
import com.sprintpay.projetsig.repository.AntennaRepository;
import com.sprintpay.projetsig.repository.PrincipalStationRepository;
import com.sprintpay.projetsig.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AntennaService {

    private AntennaRepository antennaRepository;
    private PrincipalStationRepository principalStationRepository;

    @Autowired
    public AntennaService(AntennaRepository antennaRepository, PrincipalStationRepository principalStationRepository){
        this.antennaRepository = antennaRepository;
        this.principalStationRepository = principalStationRepository;
    }

    public Antenna saveAntenna(AntennaDTO antennaDTO){

        Antenna antenna = new Antenna();
        antenna.setAntennaClass(antennaDTO.getAntennaClass());
        antenna.setAntennaName(antennaDTO.getAntennaName());
        antenna.setAntennaSize(antennaDTO.getAntennaSize());
        antenna.setAntennaType(antennaDTO.getAntennaType());
        antenna.setAntennaDirectivity(antennaDTO.getAntennaDirectivity());
        antenna.setAntennaGain(antennaDTO.getAntennaGain());
        antenna.setAntennaGainType(antennaDTO.getAntennaGainType());
        antenna.setAntennaHeigth(antennaDTO.getAntennaHeigth());
        antenna.setAzimutRadiation(antennaDTO.getAzimutRadiation());
        antenna.setCrossPolarDisc(antennaDTO.getCrossPolarDisc());
        antenna.setElevation(antennaDTO.getElevation());
        antenna.setFreqRangeFrom(antennaDTO.getFreqRangeFrom());
        antenna.setFreqRangeTo(antennaDTO.getFreqRangeTo());
        antenna.setHorBeamWidth(antennaDTO.getHorBeamWidth());
        antenna.setInsertionLoss(antennaDTO.getInsertionLoss());
        antenna.setPolarization(antennaDTO.getPolarization());
        antenna.setReferenceAntenna(antennaDTO.getReferenceAntenna());
        antenna.setRotAzimut(antennaDTO.getRotAzimut());
        antenna.setVerBeamWidth(antennaDTO.getVerBeamWidth());

        return this.antennaRepository.save(antenna);
    }

    /*public Antenna addAntennaWithPrincipalStation(AntennaDTO antennaDTO, Integer principalStationId) {
        Antenna antenna = new Antenna();

        Optional<PrincipalStation> principalStationOptional = this.principalStationRepository.findById(principalStationId);

        if (!principalStationOptional.isPresent()) {
            throw new PrincipalStationNoFoundException(String.format("PrincipalStation with id %s not found!", principalStationId));
        }

        PrincipalStation principalStation = principalStationOptional.get();
        antenna.setAntennaClass(antennaDTO.getAntennaClass());
        antenna.setAntennaName(antennaDTO.getAntennaName());
        antenna.setAntennaSize(antennaDTO.getAntennaSize());
        antenna.setAntennaType(antennaDTO.getAntennaType());
        antenna.setAntennaDirectivity(antennaDTO.getAntennaDirectivity());
        antenna.setAntennaGain(antennaDTO.getAntennaGain());
        antenna.setAntennaGainType(antennaDTO.getAntennaGainType());
        antenna.setAntennaHeigth(antennaDTO.getAntennaHeigth());
        antenna.setAzimutRadiation(antennaDTO.getAzimutRadiation());
        antenna.setCrossPolarDisc(antennaDTO.getCrossPolarDisc());
        antenna.setElevation(antennaDTO.getElevation());
        antenna.setFreqRangeFrom(antennaDTO.getFreqRangeFrom());
        antenna.setFreqRangeTo(antennaDTO.getFreqRangeTo());
        antenna.setHorBeamWidth(antennaDTO.getHorBeamWidth());
        antenna.setInsertionLoss(antennaDTO.getInsertionLoss());
        antenna.setPolarization(antennaDTO.getPolarization());
        antenna.setReferenceAntenna(antennaDTO.getReferenceAntenna());
        antenna.setRotAzimut(antennaDTO.getRotAzimut());
        antenna.setVerBeamWidth(antennaDTO.getVerBeamWidth());
        antenna.setPrincipal_station(principalStation);


        return this.antennaRepository.save(antenna);
    }*/

    public Antenna addAntennaWithPrincipalStation(AntennaDTO antennaDTO, Integer principalStationId) {
        Antenna antenna = new Antenna();

        Optional<PrincipalStation> principalStationOptional = this.principalStationRepository.findById(principalStationId);

        /*if (!principalStationOptional.isPresent()) {
            throw new PrincipalStationNoFoundException(String.format("PrincipalStation with id %s not found!", principalStationId));
        }*/

        PrincipalStation principalStation = principalStationOptional.get();
        antenna.setAntennaClass(antennaDTO.getAntennaClass());
        antenna.setAntennaName(antennaDTO.getAntennaName());
        antenna.setAntennaSize(antennaDTO.getAntennaSize());
        antenna.setAntennaType(antennaDTO.getAntennaType());
        antenna.setAntennaDirectivity(antennaDTO.getAntennaDirectivity());
        antenna.setAntennaGain(antennaDTO.getAntennaGain());
        antenna.setAntennaGainType(antennaDTO.getAntennaGainType());
        antenna.setAntennaHeigth(antennaDTO.getAntennaHeigth());
        antenna.setAzimutRadiation(antennaDTO.getAzimutRadiation());
        antenna.setCrossPolarDisc(antennaDTO.getCrossPolarDisc());
        antenna.setElevation(antennaDTO.getElevation());
        antenna.setFreqRangeFrom(antennaDTO.getFreqRangeFrom());
        antenna.setFreqRangeTo(antennaDTO.getFreqRangeTo());
        antenna.setHorBeamWidth(antennaDTO.getHorBeamWidth());
        antenna.setInsertionLoss(antennaDTO.getInsertionLoss());
        antenna.setPolarization(antennaDTO.getPolarization());
        antenna.setReferenceAntenna(antennaDTO.getReferenceAntenna());
        antenna.setRotAzimut(antennaDTO.getRotAzimut());
        antenna.setVerBeamWidth(antennaDTO.getVerBeamWidth());
        antenna.setPrincipal_station(principalStation);

        try {
            antenna.setPrincipal_station(principalStation);
            return this.antennaRepository.save(antenna);
        } catch (Exception e) {
            // Handle any other exceptions here
            throw e;
        }
    }

   /* public Page<Antenna> getAllAntenna(String search, Pageable pageable) {
        try {
            return this.antennaRepository.findAll(search == null ? null : search, pageable);
        } catch (Exception e) {
            // Log the exception or handle it in an appropriate way
            e.printStackTrace();

            // Return an appropriate response instead of throwing the exception
            // For example, you can return an empty page or a specific error page
            return Page.empty();
        }
    }*/

    public Page<Antenna> getAllAntenna(String search, Pageable pageable){
        return this.antennaRepository.findAll(search==null?null:search,pageable);
    }

    public Page<Antenna> getAllAntennaOfPrincipalStation(Integer id, String search, Pageable pageable){
        return this.antennaRepository.findAntennaByIdPrincipalStation(id, search==null?null:search, pageable);
    }

   public Optional<Antenna> getOneAntenna(Integer id){
        Optional<Antenna> antenna = this.antennaRepository.findById(id);
        /*if(!antenna.isPresent()){
            throw new AntennaNoFoundException(String.format("Antenna not found with id %s", id));
        }*/
        return antenna;
    }

    /*public Optional<Antenna> getOneAntenna(Integer id) {
        try {
            Optional<Antenna> antenna = this.antennaRepository.findById(id);
            if (!antenna.isPresent()) {
                throw new AntennaNoFoundException(String.format("Antenna not found with id %s", id));
            }
            return antenna;
        } catch (Exception e) {
            // Handle any other exceptions here
            throw e;
        }
    }*/


   /* public Antenna updateAntenna(AntennaUpdateDTO antenna, Integer id){
        Optional<Antenna> antennaExist = this.antennaRepository.findById(id);
        if(!antennaExist.isPresent()){
            throw new AntennaNoFoundException(String.format("PrincipalStation with id %s not found!",id));
        }
        Optional<PrincipalStation> principalStationExist = this.principalStationRepository.findById(antenna.getPrincipal_station().getId());
        if(!principalStationExist.isPresent()){
            throw new PrincipalStationNoFoundException(String.format("Arrondissement with id %s not found!",id));
        }
        Antenna existingAntenna = antennaExist.get();
        existingAntenna.setAntennaClass(antenna.getAntennaClass());
        existingAntenna.setAntennaName(antenna.getAntennaName());
        existingAntenna.setAntennaSize(antenna.getAntennaSize());
        existingAntenna.setAntennaType(antenna.getAntennaType());
        existingAntenna.setAntennaDirectivity(antenna.getAntennaDirectivity());
        existingAntenna.setAntennaGain(antenna.getAntennaGain());
        existingAntenna.setAntennaGainType(antenna.getAntennaGainType());
        existingAntenna.setAntennaHeigth(antenna.getAntennaHeigth());
        existingAntenna.setAzimutRadiation(antenna.getAzimutRadiation());
        existingAntenna.setCrossPolarDisc(antenna.getCrossPolarDisc());
        existingAntenna.setElevation(antenna.getElevation());
        existingAntenna.setFreqRangeFrom(antenna.getFreqRangeFrom());
        existingAntenna.setFreqRangeTo(antenna.getFreqRangeTo());
        existingAntenna.setHorBeamWidth(antenna.getHorBeamWidth());
        existingAntenna.setInsertionLoss(antenna.getInsertionLoss());
        existingAntenna.setPolarization(antenna.getPolarization());
        existingAntenna.setReferenceAntenna(antenna.getReferenceAntenna());
        existingAntenna.setRotAzimut(antenna.getRotAzimut());
        existingAntenna.setVerBeamWidth(antenna.getVerBeamWidth());
        existingAntenna.setPrincipal_station(principalStationExist.get());

        return this.antennaRepository.save(existingAntenna);
    }*/
    public Antenna updateAntenna(AntennaUpdateDTO antenna, Integer id) {
        Optional<Antenna> antennaExist = this.antennaRepository.findById(id);
        /*if (!antennaExist.isPresent()) {
            throw new AntennaNoFoundException(String.format("PrincipalStation with id %s not found!", id));
        }*/
        Optional<PrincipalStation> principalStationExist = this.principalStationRepository.findById(antenna.getPrincipal_station().getId());
        /*if (!principalStationExist.isPresent()) {
            throw new PrincipalStationNoFoundException(String.format("Arrondissement with id %s not found!", id));
        }*/

        try {
            Antenna existingAntenna = antennaExist.get();
            existingAntenna.setAntennaClass(antenna.getAntennaClass());
            existingAntenna.setAntennaName(antenna.getAntennaName());
            existingAntenna.setAntennaSize(antenna.getAntennaSize());
            existingAntenna.setAntennaType(antenna.getAntennaType());
            existingAntenna.setAntennaDirectivity(antenna.getAntennaDirectivity());
            existingAntenna.setAntennaGain(antenna.getAntennaGain());
            existingAntenna.setAntennaGainType(antenna.getAntennaGainType());
            existingAntenna.setAntennaHeigth(antenna.getAntennaHeigth());
            existingAntenna.setAzimutRadiation(antenna.getAzimutRadiation());
            existingAntenna.setCrossPolarDisc(antenna.getCrossPolarDisc());
            existingAntenna.setElevation(antenna.getElevation());
            existingAntenna.setFreqRangeFrom(antenna.getFreqRangeFrom());
            existingAntenna.setFreqRangeTo(antenna.getFreqRangeTo());
            existingAntenna.setHorBeamWidth(antenna.getHorBeamWidth());
            existingAntenna.setInsertionLoss(antenna.getInsertionLoss());
            existingAntenna.setPolarization(antenna.getPolarization());
            existingAntenna.setReferenceAntenna(antenna.getReferenceAntenna());
            existingAntenna.setRotAzimut(antenna.getRotAzimut());
            existingAntenna.setVerBeamWidth(antenna.getVerBeamWidth());

            existingAntenna.setPrincipal_station(principalStationExist.get());

            return this.antennaRepository.save(existingAntenna);
        } catch (Exception e) {
            // Handle any other exceptions here
            throw e;
        }

    }

    public void removeAntenna(Integer id){
        Optional<Antenna> antenna = this.antennaRepository.findById(id);
        /*if(!antenna.isPresent()){
            throw new AntennaNoFoundException(String.format("Antenna with id %s not found!",id));
        }*/

        try {
            this.antennaRepository.delete(antenna.get());
        } catch (Exception e) {
            // Handle any other exceptions here
            throw e;
        }
    }

    /*public void removeAntenna(Integer id){
        Optional<Antenna> antenna = this.antennaRepository.findById(id);
        if(!antenna.isPresent()){
            throw new AntennaNoFoundException(String.format("Antenna with id %s not found!",id));
        }
        this.antennaRepository.delete(antenna.get());
    }*/
}
