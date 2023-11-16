package com.sprintpay.projetsig.controller;

import com.sprintpay.projetsig.dto.UserDTO;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.service.AuthenticationService;
import com.sprintpay.projetsig.user.User;
import com.sprintpay.projetsig.utils.PaginationUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register/operator")
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserDTO request,
            @RequestParam(required = false) Integer idOperator
    ){
         return ResponseEntity.ok(service.register(request, idOperator));
    }

    @GetMapping("/token")
    public ResponseEntity<User> getUserByTokens(@RequestParam("token") String token) {
        System.out.println("le token dans getUserByTokens:"+token);
        Optional<User> userOptional = service.getUserByToken(token);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/authenticate")
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ){

        return ResponseEntity.ok(service.authenticate(request));
    }


    @GetMapping(path = "/user", name = "list")
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> list(
            @RequestParam(value = "search", required = false) String search,
            @ParameterObject Pageable pageable
    ) {
        Page<User> users = this.service.getAllUser(search, pageable);

        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), users);

        ResponseEntity<List<User>>  user = ResponseEntity.ok().headers(header).body(users.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);

        return  response;
    }


    @GetMapping(path = "/user/{id}", name = "read")
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> read(@PathVariable Integer id){
        return this.service.getOneUser(id);
    }

    @PutMapping(path = "/user/{id}", name = "update")
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.PUT})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationResponse> update(@RequestBody UserDTO userDTO, @PathVariable("id") Integer idUser, @RequestParam(required = false) Integer idOperator) {
        AuthenticationResponse authenticationResponse = this.service.updateUser(userDTO, idUser, idOperator);
        return ResponseEntity.ok().body(authenticationResponse);
    }
}
