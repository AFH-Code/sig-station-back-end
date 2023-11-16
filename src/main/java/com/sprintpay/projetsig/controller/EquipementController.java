package com.sprintpay.projetsig.controller;

import com.sprintpay.projetsig.model.Equipement;
import com.sprintpay.projetsig.service.EquipementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EquipementController {

    private EquipementService equipementService;

    public EquipementController(EquipementService equipementService) {
        this.equipementService = equipementService;
    }

    @PostMapping(path = "/equipement", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public Equipement add (@RequestBody Equipement equipement){
        return this.equipementService.saveEquipement(equipement);
    }

    @GetMapping(path = "/equipement", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<Equipement> list(){
        return this.equipementService.getAllEquipement();
    }

    @GetMapping(path = "/equipement/{id}", name = "read")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Equipement> read(@PathVariable Integer id){
        return this.equipementService.getOneEquipement(id);
    }

    @PutMapping(path = "/equipement/{id}", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public Equipement update(@RequestBody Equipement equipement, @PathVariable Integer id){
        return this.equipementService.updateEquipement(equipement, id);
    }

    @DeleteMapping(path = "/antennas/{id}", name = "remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
        this.equipementService.removeEquipement(id);
    }
}
