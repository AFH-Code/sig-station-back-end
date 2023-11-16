package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.exceptions.AntennaNoFoundException;
import com.sprintpay.projetsig.exceptions.EquipementNoFoundException;
import com.sprintpay.projetsig.model.Equipement;
import com.sprintpay.projetsig.repository.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipementService {

    private EquipementRepository equipementRepository;

    @Autowired
    public EquipementService( EquipementRepository equipementRepository){
        this.equipementRepository = equipementRepository;
    }

    public Equipement saveEquipement(Equipement equipement){
        return this.equipementRepository.save(equipement);
    }

    public List<Equipement> getAllEquipement(){
        return this.equipementRepository.findAll();
    }

    public Optional<Equipement> getOneEquipement(Integer id){
        Optional<Equipement> equipement = this.equipementRepository.findById(id);
        if(!equipement.isPresent()){
            throw new EquipementNoFoundException(String.format("Equipement with id %s not found!" + id));
        }
        return this.equipementRepository.findById(id);
    }

    public Equipement updateEquipement(Equipement equipement, Integer id){
        Optional<Equipement> EquipementExist = this.equipementRepository.findById(id);
        if(!EquipementExist.isPresent()){
            throw new EquipementNoFoundException(String.format("Equipement with id %s not found!" + id));
        }
        return this.equipementRepository.save(equipement);
    }

    public void removeEquipement(Integer id){
        Optional<Equipement> equipement = this.equipementRepository.findById(id);
        if(!equipement.isPresent()){
            throw new EquipementNoFoundException(String.format("Equipement with id %s not found!" + id));
        }
        this.equipementRepository.delete(equipement.get());
    }
}
