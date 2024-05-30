package com.rs.countrydetails.controller;

import com.rs.countrydetails.authentication.AuthRequest;
import com.rs.countrydetails.dto.Authorities;
import com.rs.countrydetails.entity.CDUser;
import com.rs.countrydetails.exception.CDException;
import com.rs.countrydetails.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repository;
    @PostMapping("/sign_up")
    public ResponseEntity<?> createUser(@RequestBody AuthRequest request) throws CDException {

            if(request==null||request.getUsername()==null||request.getPassword()==null){
                return new ResponseEntity<String>("please enter valid username and password ",HttpStatus.BAD_REQUEST);
            }
            if(repository.getUserByUsername(request.getUsername()).isPresent()){
                return new ResponseEntity<String>("username already exists",HttpStatus.BAD_REQUEST);
            }
            CDUser cdUser=new CDUser();
            cdUser.setPassword(passwordEncoder.encode(request.getPassword()));
            cdUser.setUsername(request.getUsername());
            cdUser.setAuthorities(Authorities.USER.toString()+","+Authorities.DEV.toString());
            cdUser.setIsAccountNonExpired(true);
            cdUser.setIsAccountNonLocked(true);
            repository.save(cdUser);
            return new ResponseEntity<>(request,HttpStatus.CREATED);
    }
//    @GetMapping("/authenticate")
//    public String authenticate(@RequestBody AuthRequest request){
//
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
//
//        Authentication authentication=authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        try {
//           if (authentication.isAuthenticated()) {
//               return "success";
//           }
//        }
//        catch (Exception e){
//
//        }
//
//        return "credentials not found";
//
//    }

}
