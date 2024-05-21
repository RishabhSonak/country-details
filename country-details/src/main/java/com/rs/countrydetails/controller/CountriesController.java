package com.rs.countrydetails.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
public class CountriesController {

    @GetMapping("/test")
    public String test(){
        return "success";
    }

}
