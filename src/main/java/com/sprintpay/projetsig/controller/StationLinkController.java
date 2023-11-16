package com.sprintpay.projetsig.controller;

import com.sprintpay.projetsig.dto.OperatorDTO;
import com.sprintpay.projetsig.dto.StationLinkDTO;
import com.sprintpay.projetsig.model.Operator;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.model.StationLink;
import com.sprintpay.projetsig.service.StationLinkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StationLinkController {

    private final StationLinkService stationLinkService;

    public StationLinkController(StationLinkService stationLinkService) {
        this.stationLinkService = stationLinkService;
    }

    @PutMapping(path = "/stationLink/setAllLinks", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public List<StationLink> updateAllLinks(){
        return this.stationLinkService.updateStationLinks();
    }

    @PostMapping(path = "/stationLink", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public StationLink add (@RequestBody StationLinkDTO stationLinkDTO){
        return this.stationLinkService.saveStationLink(stationLinkDTO);
    }


    @GetMapping(path = "/stationLink", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<StationLink> list(){
        return this.stationLinkService.getAllStationLink();
    }

    @GetMapping(path = "/stationLink/{id}", name = "read")
    @ResponseStatus(HttpStatus.OK)
    public Optional<StationLink> read(@PathVariable Integer id){
        return this.stationLinkService.getOneStationLink(id);
    }

    /*@PutMapping(path = "/stationLink/{id}", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public StationLink update(@RequestBody StationLink oparator, @PathVariable Integer id){
        return this.stationLinkService.updateOperator(oparator, id);
    }


    @DeleteMapping(path = "/stationLink/{id}", name = "remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
        this.stationLinkService.removeOperator(id);
    }*/
}
