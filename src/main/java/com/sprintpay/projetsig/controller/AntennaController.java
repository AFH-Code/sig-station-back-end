package com.sprintpay.projetsig.controller;

import com.sprintpay.projetsig.dto.AntennaDTO;
import com.sprintpay.projetsig.dto.AntennaUpdateDTO;
import com.sprintpay.projetsig.model.Antenna;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.model.TypeStationP;
import com.sprintpay.projetsig.service.AntennaService;
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
public class AntennaController {
    private AntennaService antennaService;

    public AntennaController(AntennaService antennaService) {
        this.antennaService = antennaService;
    }

    @PostMapping(path = "/antenna", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public Antenna add (@RequestBody AntennaDTO antennaDTO){
        return this.antennaService.saveAntenna(antennaDTO);
    }

    @PostMapping(path = "/antenna/principalStation/{id}", name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public Antenna addAntennaWithPrincipalStation (@RequestBody AntennaDTO antennaDTO, @PathVariable Integer id){
        return this.antennaService.addAntennaWithPrincipalStation(antennaDTO, id);
    }

    @GetMapping(path = "/antenna", name = "list")
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> list(
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
    ) {
        Page<Antenna> antennas = this.antennaService.getAllAntenna(search, pageable);

        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), antennas);

        ResponseEntity<List<Antenna>>  antenna = ResponseEntity.ok().headers(header).body(antennas.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("antenna", antenna);

        return  response;
    }

    @GetMapping(path = "/antenna/principalStation/{id}", name = "list")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> listAntennaByPrincipalStation(
            @PathVariable("id") Integer id,
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
    ){
        Page<Antenna>  antennas= this.antennaService.getAllAntennaOfPrincipalStation(id, search, pageable);
        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), antennas);
        ResponseEntity<List<Antenna>>  listeAntenna = ResponseEntity.ok().headers(header).body(antennas.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("antenna", listeAntenna);

        return  response;
    }

    @GetMapping(path = "/antenna/{id}", name = "read")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Antenna> read(@PathVariable Integer id){
        return this.antennaService.getOneAntenna(id);
    }

    @PutMapping(path = "/antenna/{id}", name = "update")
    @ResponseStatus(HttpStatus.OK)
    public Antenna update(@RequestBody AntennaUpdateDTO antenna, @PathVariable Integer id){
        return this.antennaService.updateAntenna(antenna, id);
    }

    @DeleteMapping(path = "/antenna/{id}", name = "remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
        this.antennaService.removeAntenna(id);
    }
}
