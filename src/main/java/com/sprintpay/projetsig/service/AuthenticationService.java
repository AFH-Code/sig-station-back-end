package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.configuration.JwtService;
import com.sprintpay.projetsig.controller.AuthenticationRequest;
import com.sprintpay.projetsig.controller.AuthenticationResponse;
import com.sprintpay.projetsig.controller.RegisterRequest;
import com.sprintpay.projetsig.dto.UserDTO;
import com.sprintpay.projetsig.exceptions.OperatorNoFoundException;
import com.sprintpay.projetsig.exceptions.UserNoFoundException;
import com.sprintpay.projetsig.model.Operator;
import com.sprintpay.projetsig.repository.OperatorRepository;
import com.sprintpay.projetsig.user.Role;
import com.sprintpay.projetsig.user.User;
import com.sprintpay.projetsig.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OperatorRepository operatorRepository;

    public AuthenticationResponse register(UserDTO request, Integer idOperator) {
        User.UserBuilder userBuilder = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole());

        if (idOperator != null) {
            Optional<Operator> operatorOptional = this.operatorRepository.findById(idOperator);
            if (!operatorOptional.isPresent()) {
                throw new OperatorNoFoundException(String.format("Operator with id %s not found!", idOperator));
            }
            Operator operator = operatorOptional.get();
            userBuilder.operator(operator);

            if ("ADMIN_ORG".equalsIgnoreCase(String.valueOf(request.getRole()))) {
                userBuilder.role(Role.ADMIN_ORG);
            } else if ("SUPER_ADMIN_ORG".equalsIgnoreCase(String.valueOf(request.getRole()))) {
                userBuilder.role(Role.SUPER_ADMIN_ORG);
            } else {
                throw new IllegalArgumentException("Invalid role value");
            }
        } else {
            if ("USER".equalsIgnoreCase(String.valueOf(request.getRole())) || "ADMIN".equalsIgnoreCase(String.valueOf(request.getRole()))) {
                // Do not add the operator
            } else {
                throw new IllegalArgumentException("Invalid role value");
            }
        }

        User user = userBuilder.build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Optional<User> getUserByToken(String token) {
        System.out.println("le token dans getUserByToken:"+token);
        String email = jwtService.extractEmailFromToken(token);
        return repository.findByEmail(email);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /*public List<User> getAllUser(){
        return this.repository.findAll();
    }*/

    public Page<User> getAllUser(String search, Pageable pageable){
        return this.repository.findAll(search==null?null:search,pageable);
    }


    public Optional<User> getOneUser(Integer id){
        Optional<User> user = this.repository.findById(id);
        if(!user.isPresent()){
            throw new UserNoFoundException(String.format("User with id %s not found!" + id));
        }
        return this.repository.findById(id);
    }

   /* public AuthenticationResponse updateUser(UserDTO request, Integer idUser, Integer idOperator) {
        Optional<Operator> operatorOptional = this.operatorRepository.findById(idOperator);

        if (!operatorOptional.isPresent()) {
            throw new OperatorNoFoundException(String.format("Operator with id %s not found!", idOperator));
        }

        Optional<User> userOptional = repository.findById(idUser);
        if (!userOptional.isPresent()) {
            throw new UserNoFoundException(String.format("User with id %s not found!", idUser));
        }

        User user = userOptional.get();

        // Update the properties of the retrieved user
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        if ("USER".equalsIgnoreCase(String.valueOf(request.getRole())) || "ADMIN".equalsIgnoreCase(String.valueOf(request.getRole()))) {
            // Do not add the operator
        } else if ("ADMIN_ORG".equalsIgnoreCase(String.valueOf(request.getRole())) || "SUPER_ADMIN_ORG".equalsIgnoreCase(String.valueOf(request.getRole()))) {
            user.setOperator(operatorOptional.get());
            user.setRole(Role.ADMIN_ORG); // Set the role to "ADMIN_ORG" instead of "SUPER_ADMIN_ORG"
        } else {
            throw new IllegalArgumentException("Invalid role value");
        }

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }*/
   public AuthenticationResponse updateUser(UserDTO request, Integer idUser, Integer idOperator) {

       Optional<User> userOptional = repository.findById(idUser);
       if (!userOptional.isPresent()) {
           throw new UserNoFoundException(String.format("User with id %s not found!", idUser));
       }

       User user = userOptional.get();
// Update the properties of the retrieved user
       user.setFirstname(request.getFirstname());
       user.setLastname(request.getLastname());
       user.setEmail(request.getEmail());
       user.setPassword(passwordEncoder.encode(request.getPassword()));
       user.setRole(request.getRole());

       if (idOperator != null) {
           Optional<Operator> operatorOptional = this.operatorRepository.findById(idOperator);
           if (!operatorOptional.isPresent()) {
               throw new OperatorNoFoundException(String.format("Operator with id %s not found!", idOperator));
           }
           user.setOperator(operatorOptional.get());

           if ("ADMIN_ORG".equalsIgnoreCase(String.valueOf(request.getRole()))) {
               user.setRole(Role.ADMIN_ORG);
           } else if ("SUPER_ADMIN_ORG".equalsIgnoreCase(String.valueOf(request.getRole()))) {
               user.setRole(Role.SUPER_ADMIN_ORG);
           }else {
               throw new IllegalArgumentException("Invalid role value");
           }
       } else {
           if ("USER".equalsIgnoreCase(String.valueOf(request.getRole())) || "ADMIN".equalsIgnoreCase(String.valueOf(request.getRole()))) {
               // Do not add the operator
               user.setOperator(null); // Remove the organization association
           } else {
               throw new IllegalArgumentException("Invalid role value");
           }
       }

       repository.save(user);

       var jwtToken = jwtService.generateToken(user);
       return AuthenticationResponse.builder()
               .token(jwtToken)
               .build();
   }
}
