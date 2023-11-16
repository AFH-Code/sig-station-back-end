package com.sprintpay.projetsig.controller;

import com.sprintpay.projetsig.dto.OperatorDTO;
import com.sprintpay.projetsig.model.Antenna;
import com.sprintpay.projetsig.model.Operator;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.service.OperatorService;
import com.sprintpay.projetsig.utils.PaginationUtil;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OperatorController {
    private OperatorService operatorService;


    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @PostMapping(path = "/operator", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public Operator add (@RequestBody OperatorDTO operatorDTO){
        return this.operatorService.saveOperator(operatorDTO);
    }

    @GetMapping(path = "/operator", name = "list")
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> list(
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
    ) {
        Page<Operator> operators = this.operatorService.getAllOperator(search, pageable);

        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), operators);

        ResponseEntity<List<Operator>>  operator = ResponseEntity.ok().headers(header).body(operators.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("operator", operator);

        return  response;
    }

    @GetMapping(path = "/operator/stationCount", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> list(){
        return this.operatorService.getAllOperatorWithPrincipalStationCount();
    }


    @GetMapping(path = "/operator/{id}", name = "read")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Operator> read(@PathVariable Integer id){
        return this.operatorService.getOneOperator(id);
    }

    @GetMapping(path = "/operator/principalStations/{operatorId}", name = "read")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> readPrincipalStations(
            @PathVariable Integer operatorId,
            @ParameterObject Pageable pageable
    ){
        Page<PrincipalStation> principalStations = this.operatorService.getPrincipalStationsByOperatorId(operatorId, pageable);
        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStations);
        ResponseEntity<List<PrincipalStation>>  listePrincipalStation = ResponseEntity.ok().headers(header).body(principalStations.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("principalStation", listePrincipalStation);
        return response;
    }
    @PutMapping(path = "/operator/{id}", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public Operator update(@RequestBody OperatorDTO oparator, @PathVariable Integer id){
        return this.operatorService.updateOperator(oparator, id);
    }
    @PutMapping(path = "/principalStation/{idOperator}/{idStation}", name = "update")
    @ResponseStatus(HttpStatus.CREATED)
    public PrincipalStation addArrondissementToPrincipalStation (
            @PathVariable Integer idOperator,
            @PathVariable Integer idStation){
        return this.operatorService.addOperatorToPrincipalStation(idOperator, idStation);
    }

    @DeleteMapping(path = "/operator/{id}", name = "remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
        this.operatorService.removeOperator(id);
    }
}
