package com.rs.countrydetails.controller;

import com.rs.countrydetails.authentication.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/test")
    public AuthRequest test(){
        AuthRequest authRequest=new AuthRequest();
        authRequest.setUsername("ram");
        authRequest.setPassword("ramram");
        return authRequest;

    }
    @GetMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequest request){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());

        Authentication authentication=authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        try {
           if (authentication.isAuthenticated()) {
               return "success";
           }
        }
        catch (Exception e){

        }

        return "credentials not found";

    }

}
