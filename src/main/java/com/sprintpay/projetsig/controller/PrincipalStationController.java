package com.sprintpay.projetsig.controller;

import com.sprintpay.projetsig.dto.PrincipalStationDTO;
import com.sprintpay.projetsig.dto.PrincipalStationUpdateDTO;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.model.TypeStationP;
import com.sprintpay.projetsig.service.PrincipalStationQueryService;
import com.sprintpay.projetsig.service.PrincipalStationService;
import com.sprintpay.projetsig.utils.PaginationUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.locationtech.jts.geom.Geometry;
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
public class PrincipalStationController {

    private final PrincipalStationService principalStationService;
    private final PrincipalStationQueryService principalStationQueryService;

    public PrincipalStationController(PrincipalStationService principalStationService, PrincipalStationQueryService principalStationQueryService) {
        this.principalStationService = principalStationService;
        this.principalStationQueryService = principalStationQueryService;
    }



    /*@GetMapping(path = "/geometry/id/{id}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public String convertToGeoJson(@PathVariable("id") Integer id) {
            return principalStationService.convertToGeoJson(id);
    }*/


    @PostMapping(path = "/principalStation", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public PrincipalStation add (@RequestBody PrincipalStationDTO principalStation){
        return this.principalStationService.savePrincipalStation(principalStation);
    }

    @PostMapping(path = "/principalStation/createVues", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(){
        this.principalStationService.initializeViews();
    }

    @PostMapping(path = "/principalStation/arrondissement/{arronId}/operator/{operatorId}", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public PrincipalStation addStationWithArrondissementAndOperator (@RequestBody PrincipalStationDTO principalStation,  @PathVariable Integer arronId, @PathVariable Integer operatorId){
        return this.principalStationService.addPrincipalStationWithArrondissementAndOperator(principalStation, arronId, operatorId);
    }

    /*@PutMapping(path = "/principalStation/{idArrond}/{idStation}", name = "update")
    @ResponseStatus(HttpStatus.CREATED)
    public PrincipalStation addArrondissementToPrincipalStation (
            @PathVariable Integer idArrond,
            @PathVariable Integer idStation){
        return this.principalStationService.addArrondissementToPrincipalStation(idArrond, idStation);
    }*/

    @GetMapping(path = "/principalStation", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PrincipalStation>> list(
            @ParameterObject Pageable pageable
    ){
        Page<PrincipalStation> principalStations = this.principalStationService.getAllPrincipalStation(pageable);
        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStations);
        return ResponseEntity.ok().headers(header).body(principalStations.getContent());
    }


    @GetMapping(path = "/principalStation/type", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> listPrincipalStationByType(
            @RequestParam(value = "idOperator", required = false) Integer idOperator,
            @RequestParam(required = false) List<TypeStationP> type,
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
            ){

        Page<PrincipalStation>  principalStations= this.principalStationService.getAllPrincipalStationByType(idOperator,type, search, pageable);
        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStations);

        ResponseEntity<List<PrincipalStation>>  listePrincipalStation = ResponseEntity.ok().headers(header).body(principalStations.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("principalStation", listePrincipalStation);

        return  response;
    }

    @GetMapping(path = "/principalStation/operator/{id}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> listPrincipalStationByOperator(
            @PathVariable("id") Integer id,
            //@RequestParam(required = false) List<TypeStationP> type,
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
    ){

        Page<PrincipalStation>  principalStations= this.principalStationService.getAllPrincipalStationOfOperator(id,search, pageable);
        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStations);
        ResponseEntity<List<PrincipalStation>>  listePrincipalStation = ResponseEntity.ok().headers(header).body(principalStations.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("principalStation", listePrincipalStation);

        return  response;
    }

   /* @GetMapping(path = "/principalStation/arrondissement/{id}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> listPrincipalStationByArrondissement(
            @PathVariable("id") Integer id,
            @RequestParam(required = false) List<TypeStationP> type,
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
            ){

        Page<PrincipalStation>  principalStations= this.principalStationService.getAllPrincipalStationOfArrandissement(id, type, search, pageable);
        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStations);
        ResponseEntity<List<PrincipalStation>>  listePrincipalStation = ResponseEntity.ok().headers(header).body(principalStations.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("principalStation", listePrincipalStation);

        return  response;
    }*/
   @GetMapping(path = "/principalStation/arrondissement/{idArrondissement}", name = "list")
   @ResponseStatus(HttpStatus.OK)
   public Map<String, Object> listPrincipalStationByArrondissementAndOperator(
           @PathVariable("idArrondissement") Integer idArrondissement,
           @RequestParam(value = "idOperator", required = false) Integer idOperator,
           @RequestParam(required = false) List<TypeStationP> type,
           @RequestParam(value = "search", required = false) String search,
           @ParameterObject Pageable pageable
   ) {
       Page<PrincipalStation> principalStations = this.principalStationService.getAllPrincipalStationOfArrandissementAndOperator(idArrondissement, idOperator, type, search, pageable);
       HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStations);
       ResponseEntity<List<PrincipalStation>> listePrincipalStation = ResponseEntity.ok().headers(header).body(principalStations.getContent());
       Map<String, Object> response = new HashMap<>();
       response.put("principalStation", listePrincipalStation);

       return response;
   }

    /*@GetMapping(path = "/principalStation/departement/{id}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> listPrincipalStationByDepartement(
            @PathVariable("id") Integer id,
            @RequestParam(required = false) List<TypeStationP> type,
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
            ){
        Page<PrincipalStation>  principalStationss= this.principalStationService.getAllPrincipalStationOfDepartement(id, type, search, pageable);

        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStationss);
        ResponseEntity<List<PrincipalStation>>  listePrincipalStation = ResponseEntity.ok().headers(header).body(principalStationss.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("principalStation", listePrincipalStation);

        return  response;
    }*/
    @GetMapping(path = "/principalStation/departement/{idDepartement}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> listPrincipalStationByDepartementAndOperator(
            @PathVariable("idDepartement") Integer idDepartement,
            @RequestParam(value = "idOperator", required = false) Integer idOperator,
            @RequestParam(required = false) List<TypeStationP> type,
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
    ) {
        Page<PrincipalStation> principalStations = this.principalStationService.getAllPrincipalStationOfDepartementAndOperator(idDepartement, idOperator, type, search, pageable);

        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStations);
        ResponseEntity<List<PrincipalStation>> listePrincipalStation = ResponseEntity.ok().headers(header).body(principalStations.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("principalStation", listePrincipalStation);

        return response;
    }

    /*@GetMapping(path = "/principalStation/region/{id}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> listPrincipalStationByRegion(
            @PathVariable("id") Integer id,
            @RequestParam(required = false) List<TypeStationP> type,
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
    ){

        Page<PrincipalStation>  principalStationsss= this.principalStationService.getAllPrincipalStationOfRegion(id, type, search, pageable);

        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStationsss);

        ResponseEntity<List<PrincipalStation>>  listePrincipalStation = ResponseEntity.ok().headers(header).body(principalStationsss.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("principalStation", listePrincipalStation);

        return  response;
    }*/
    @GetMapping(path = "/principalStation/region/{idRegion}", name = "listByRegionAndOperator")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> listPrincipalStationByRegionAndOperator(
            @PathVariable("idRegion") Integer idRegion,
            @RequestParam(value = "idOperator", required = false) Integer idOperator,
            @RequestParam(required = false) List<TypeStationP> type,
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
    ) {
        Page<PrincipalStation> principalStations = this.principalStationService.getAllPrincipalStationOfRegionAndOperator(idRegion, idOperator, type, search, pageable);

        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), principalStations);

        ResponseEntity<List<PrincipalStation>> listePrincipalStation = ResponseEntity.ok().headers(header).body(principalStations.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("principalStation", listePrincipalStation);

        return response;
    }

    @GetMapping(path = "/principalStation/{id}", name = "read")
    @ResponseStatus(HttpStatus.OK)
    public Optional<PrincipalStation> read(@PathVariable Integer id){
        return this.principalStationService.getOnePrincipalStation(id);
    }

    @PutMapping(path = "/principalStation/id/{id}", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public PrincipalStation update(
            @PathVariable("id") Integer id,
            @RequestBody PrincipalStationUpdateDTO principalStation
                                   ){
        PrincipalStation principalStation1 = new PrincipalStation();
        return this.principalStationService.updatePrincipalStation(principalStation, id);
    }
    @PutMapping(path = "/principalStation/validate/id/{id}", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public PrincipalStation update(
            @PathVariable("id") Integer id
    ){
        PrincipalStation principalStation1 = new PrincipalStation();
        return this.principalStationService.validate(id);
    }

    @PutMapping(path = "/principalStation/setAllPoints", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public List<PrincipalStation> updateAllPoints(){
        return this.principalStationService.updatePrincipalStations();
    }

    @DeleteMapping(path = "/principalStation/{id}", name = "remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
        this.principalStationService.removePrincipalStation(id);
    }

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    @ResponseBody
    public String exampleMethod(HttpServletResponse response) {
        // Utilisez l'objet HttpServletResponse pour envoyer la réponse au client
        // ...
        return "Exemple de réponse";
    }
}
