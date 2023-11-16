package com.sprintpay.projetsig.controller;

import com.sprintpay.projetsig.model.Region;
import com.sprintpay.projetsig.service.RegionService;
import org.locationtech.jts.geom.Geometry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RegionController {
    private RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping(path = "/region/geometry/{id}", name = "read")
    public ResponseEntity<String> convertToGeoJson(@PathVariable Integer id) {
        String geoJson = regionService.convertToGeoJson(id);
        return ResponseEntity.ok().body(geoJson);
    }

    @PostMapping(path = "/region", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public Region add (@RequestBody Region region){
        return this.regionService.saveRegion(region);
    }

    @GetMapping(path = "/region", name = "list")
    @CrossOrigin(origins = "https://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.PUT})
    @ResponseStatus(HttpStatus.OK)
    public List<Region> list(){
        return this.regionService.getAllRegion();
    }

    @GetMapping(path = "/region/{id}", name = "read")
    @CrossOrigin(origins = "https://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.PUT})
    @ResponseStatus(HttpStatus.OK)
    public Optional<Region> read(@PathVariable Integer id){
        return this.regionService.getOneRegion(id);
    }

    @PutMapping(path = "/region/{id}", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public Region update(@RequestBody Region region, @PathVariable Integer id){
        return this.regionService.updateRegion(region, id);
    }

    @DeleteMapping(path = "/region/{id}", name = "remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
        this.regionService.removeRegion(id);
    }
}
