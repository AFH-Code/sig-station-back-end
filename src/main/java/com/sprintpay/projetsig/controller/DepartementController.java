package com.sprintpay.projetsig.controller;

import com.sprintpay.projetsig.model.Departement;
import com.sprintpay.projetsig.service.DepartementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class DepartementController {

    private DepartementService departementService;

    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @GetMapping(path = "/departement/geometry/{id}", name = "read")
    public ResponseEntity<String> convertToGeoJson(@PathVariable Integer id) {
        String geoJson = departementService.convertToGeoJson(id);
        return ResponseEntity.ok().body(geoJson);
    }

    @PostMapping(path = "/departement", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public Departement add (@RequestBody Departement departement){
        return this.departementService.saveDepartement(departement);
    }

    @GetMapping(path = "/departement", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<Departement> list(){
        return this.departementService.getAllDepartement();
    }

    @GetMapping(path = "/departement/search/{search}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<Departement> listDepartementBySearch(@PathVariable("search") String search){
        return this.departementService.getAllDepartements("%"+search+"%");
    }

    @GetMapping(path = "/departement/{id}", name = "read")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Departement> read(@PathVariable Integer id){
        return this.departementService.getOneDepartement(id);
    }

    @PutMapping(path = "/departement/{id}", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public Departement update(@RequestBody Departement departement, @PathVariable Integer id){
        return this.departementService.updateDepartement(departement, id);
    }

    @DeleteMapping(path = "/departement/{id}", name = "remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
        this.departementService.removeDepartement(id);
    }
}
