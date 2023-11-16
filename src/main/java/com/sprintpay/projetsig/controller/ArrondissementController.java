package com.sprintpay.projetsig.controller;

import com.sprintpay.projetsig.model.Arrondissement;
import com.sprintpay.projetsig.service.ArrondissementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ArrondissementController {

    private ArrondissementService arrondissementService;

    public ArrondissementController(ArrondissementService arrondissementService) {
        this.arrondissementService = arrondissementService;
    }

    @GetMapping(path = "/arrondissement/geometry/{id}", name = "read")
    public ResponseEntity<String> convertToGeoJson(@PathVariable Integer id) {
        String geoJson = arrondissementService.convertToGeoJson(id);
        return ResponseEntity.ok().body(geoJson);
    }

    @PostMapping(path = "/arrondissement", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public Arrondissement add (@RequestBody Arrondissement arrondissement){
        return this.arrondissementService.saveArrondissement(arrondissement);
    }

    @GetMapping(path = "/arrondissement", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<Arrondissement> list(){
        return this.arrondissementService.getAllArrondissement();
    }

    @GetMapping(path = "/arrondissement/idDepart/{id}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<Arrondissement> listArrondissementByDepartement(@PathVariable("id") Integer id){
        return this.arrondissementService.getAllArrondissementByArrondissement(id);
    }

    @GetMapping(path = "/arrondissement/{search}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<Arrondissement> listArrondissementBySearch(@PathVariable("search") String search){
        return this.arrondissementService.getAllArrondissements("%"+search+"%");
    }

    @GetMapping(path = "/arrondissement/id/{id}", name = "read")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Arrondissement> read(@PathVariable Integer id){
        return this.arrondissementService.getOneArrondissement(id);
    }

    @PutMapping(path = "/arrondissement/{id}", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public Arrondissement update(@RequestBody Arrondissement arrondissement, @PathVariable Integer id){
        return this.arrondissementService.updateArrondissement(arrondissement, id);
    }

    @DeleteMapping(path = "/arrondissement/{id}", name = "remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
        this.arrondissementService.removeArrondissement(id);
    }
}
