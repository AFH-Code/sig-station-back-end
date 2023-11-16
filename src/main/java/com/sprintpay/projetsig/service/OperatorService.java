package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.dto.OperatorDTO;
import com.sprintpay.projetsig.exceptions.OperatorNoFoundException;
import com.sprintpay.projetsig.exceptions.PrincipalStationNoFoundException;
import com.sprintpay.projetsig.model.Antenna;
import com.sprintpay.projetsig.model.Operator;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.repository.OperatorRepository;
import com.sprintpay.projetsig.repository.PrincipalStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OperatorService {

    private OperatorRepository operatorRepository;
    private PrincipalStationRepository principalStationRepository;

    @Autowired
    public OperatorService(OperatorRepository operatorRepository, PrincipalStationRepository principalStationRepository){
        this.operatorRepository = operatorRepository;
        this.principalStationRepository = principalStationRepository;
    }

    public Operator saveOperator(OperatorDTO operatorDTO){
        Operator operator = new Operator();
        operator.setBillingName(operatorDTO.getBillingName());
        operator.setEmail(operatorDTO.getEmail());
        operator.setFax(operatorDTO.getFax());
        operator.setOwnerAdress(operatorDTO.getOwnerAdress());
        operator.setOwnerName(operatorDTO.getOwnerName());
        operator.setTelephone(operatorDTO.getTelephone());
        operator.setTelex(operatorDTO.getTelex());
        return this.operatorRepository.save(operator);
    }

    public PrincipalStation addOperatorToPrincipalStation(Integer idOperator, Integer idStation){

        Optional<Operator> operator = this.operatorRepository.findById(idOperator);

        if(!operator.isPresent()){
            throw new OperatorNoFoundException(String.format("Arrondissement with id %s not found!",idOperator));
        }

        Optional<PrincipalStation> principalStation = this.principalStationRepository.findById(idStation);

        if(!principalStation.isPresent()){
            throw new PrincipalStationNoFoundException(String.format("PrincipalStaion with id %s not found!",idStation));
        }
        principalStation.get().setOperator(operator.get());

        return this.principalStationRepository.save(principalStation.get());
    }

    public Page<Operator> getAllOperator(String search, Pageable pageable){
        return this.operatorRepository.findAll(search==null?null:search,pageable);
    }
    public List<Map<String, Object>> getAllOperatorWithPrincipalStationCount() {
        List<Operator> operators = this.operatorRepository.findAll();
        List<Map<String, Object>> operatorList = new ArrayList<>();

        for (Operator operator : operators) {
            List<PrincipalStation> principalStations = this.principalStationRepository.findAll();// Get the PrincipalStations associated with the operator
            int principalStationCount = principalStations.size();

            String ownerName = operator.getOwnerName().toUpperCase();
            int additionalPrincipalStationCount = 0;

            for (PrincipalStation principalStation : principalStations) {
                String operatorName = principalStation.getOperatorName();
                if (operatorName != null && operatorName.equalsIgnoreCase(ownerName)) {
                    additionalPrincipalStationCount++;
                }
            }
            principalStationCount = additionalPrincipalStationCount;

            Map<String, Object> operatorMap = new HashMap<>();
            operatorMap.put("ownerName", operator.getOwnerName());
            operatorMap.put("id", operator.getId());
            operatorMap.put("ownerAddress", operator.getOwnerAdress());
            operatorMap.put("billingName", operator.getBillingName());
            operatorMap.put("telephone", operator.getTelephone());
            operatorMap.put("telex", operator.getTelex());
            operatorMap.put("fax", operator.getFax());
            operatorMap.put("email", operator.getEmail());
            operatorMap.put("nombrePrincipalStation", principalStationCount);

            operatorList.add(operatorMap);
        }

        return operatorList;
    }

    /*public List<Map<String, Object>> getAllOperatorWithPrincipalStationCount1() {
        List<Operator> operators = this.operatorRepository.findAll();
        List<PrincipalStation> principalStations = this.principalStationRepository.findAll();
        List<Map<String, Object>> operatorList = new ArrayList<>();
        for (Operator operator : operators) {
            for (PrincipalStation principalStation : principalStations) {
                //if (principalStation.getOperator().equals(operator)) {
                    String ownerName = operator.getOwnerName();
                    String operatorName = principalStation.getOperatorName();
                    if((ownerName.equals(operatorName) && operator.getPrincipalStation().isEmpty())){
                        int principalStationCount1 = principalStations.size();
                    //}
                    int principalStationCount2 = operator.getPrincipalStation().size();

                    Map<String, Object> operatorMap = new HashMap<>();
                    operatorMap.put("ownerName", operator.getOwnerName());
                    operatorMap.put("id", operator.getId());
                    operatorMap.put("ownerAddress", operator.getOwnerAdress());
                    operatorMap.put("billingName", operator.getBillingName());
                    operatorMap.put("telephone", operator.getTelephone());
                    operatorMap.put("telex", operator.getTelex());
                    operatorMap.put("fax", operator.getFax());
                    operatorMap.put("email", operator.getEmail());
                    operatorMap.put("nombrePrincipalStation", principalStationCount2+principalStationCount1);

                    operatorList.add(operatorMap);
                }
            }
        }

        return operatorList;
    }*/


    public Optional<Operator> getOneOperator(Integer id){
        Optional<Operator> operator = this.operatorRepository.findById(id);
        if(!operator.isPresent()){
            throw new OperatorNoFoundException(String.format("operator with id %s not found!" + id));
        }
        return this.operatorRepository.findById(id);
    }

    public Page<PrincipalStation> getPrincipalStationsByOperatorId(Integer operatorId, Pageable pageable) {
        Optional<Operator> operator = this.operatorRepository.findById(operatorId);
        if (!operator.isPresent()) {
            throw new OperatorNoFoundException(String.format("Operator with id %s not found!", operatorId));
        }
        return this.principalStationRepository.findByOperator(operatorId, pageable);
    }


    public Operator updateOperator(OperatorDTO operatorDTO, Integer id) {
        Optional<Operator> operatorExist = this.operatorRepository.findById(id);
        if (!operatorExist.isPresent()) {
            throw new OperatorNoFoundException(String.format("Operator with id %s not found!", id));
        }

        Operator operator = operatorExist.get();
        operator.setBillingName(operatorDTO.getBillingName());
        operator.setEmail(operatorDTO.getEmail());
        operator.setFax(operatorDTO.getFax());
        operator.setOwnerAdress(operatorDTO.getOwnerAdress());
        operator.setOwnerName(operatorDTO.getOwnerName());
        operator.setTelephone(operatorDTO.getTelephone());
        operator.setTelex(operatorDTO.getTelex());

        return this.operatorRepository.save(operator);
    }

    public void removeOperator(Integer id){
        Optional<Operator> operator = this.operatorRepository.findById(id);
        if(!operator.isPresent()){
            throw new OperatorNoFoundException(String.format("operator with id %s not found!" + id));
        }
        this.operatorRepository.delete(operator.get());
    }
}
