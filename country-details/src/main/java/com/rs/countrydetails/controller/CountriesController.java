package com.rs.countrydetails.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rs.countrydetails.exception.CDException;
import com.rs.countrydetails.utility.RequiredCountriesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/countries")
public class CountriesController {

    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private final ObjectMapper objectMapper;

    public CountriesController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello(){
        return new ResponseEntity<>("Hi",HttpStatus.OK);
    }
    @GetMapping(value = "/get_country_details/{country_name}")
    public ResponseEntity<String> getCountryDetailsByName(@PathVariable(name = "country_name") String countryName) throws CDException {
        String uri = "https://restcountries.com/v3.1/name/" + countryName;
        try {
            ResponseEntity<String> json = restTemplate.getForEntity(uri, String.class);
            return new ResponseEntity<>(json.getBody(), HttpStatus.FOUND);
        } catch (Exception exception) {
            throw new CDException("An Internal Error has occured, Please try again after some time");
        }
    }

    @GetMapping(value = "/population_greater_then/{population}")
    public ResponseEntity<List<Map<String, Object>>> populationGreaterThenN(@PathVariable(name = "population") Integer population,
                                                                            @RequestParam(name = "page_size") Integer pageSize,
                                                                            @RequestParam(name = "page_num") Integer pageNum,
                                                                            @RequestParam(name = "sort", required = false) Boolean withSort,
                                                                            @RequestParam(name = "sort_direction", required = false) String sortdirection) throws CDException {

        List<Map<String, Object>> populationGreaterThenList = new ArrayList<>();   //in this list we will store all countries wose population is greater then population obj

        List<Map<String, Object>> finalList = new ArrayList<>(); // this list will store final list after pagination

        List<Map<String, Object>> dataList = new ArrayList<>();

        Integer endIndex = pageNum * pageSize - 1; // finding end index of page for pagination
        Integer startIndex = endIndex - pageSize + 1;  // finding start index of page for pagination

        final String uri = "https://restcountries.com/v3.1/all";
        try {
            assert uri != null;
            String jsonForAllCountries = restTemplate.getForObject(uri, String.class); // getting json data from all countries
            dataList =  objectMapper.readValue(jsonForAllCountries, new TypeReference<>(){}); //converting the json to List of Maps, each map containg 1 json object

        } catch (Exception exception) {
            throw new CDException("An Internal Error has occured, Please try again after some time");
        }

        /*
        following for loop will get all the countries whose population is greater then provided
        population and populates it into populationGreaterThenList
         */
        for (Map<String, Object> jsonObj : dataList) {
            Object populationObj = jsonObj.get("population"); //getting population from json Object
            Integer actualPopulation = (Integer) populationObj; // converting populatio to Integer
            if (actualPopulation >= population) {   // if population>client require population then add it to populationGreaterthenList
                populationGreaterThenList.add(jsonObj);
            }
        }

        /*
        following if statement is for checking if client wants sorted list or not
        if withSort is not null and  withSort is true then it will execute
        its inner if statements are checking sort direction
        sorting is done using comparator;
        providing comparator into sort method of Collections
        */
        if (withSort != null && withSort.equals(true)) {
            String direction = sortdirection.toLowerCase();
            RequiredCountriesList.sortCountriesPopulation(populationGreaterThenList); // sorting by using custom class RequiredCountriesList
            if (direction.equals("asc")) {

                finalList = RequiredCountriesList.createFinalPaginatedCountriesList(populationGreaterThenList, endIndex, startIndex); // creating paginated redult by using custom class RequiredCountriesList

            }
            if (direction.equals("desc")) {

                Collections.reverse(populationGreaterThenList); // reversing the sorted list
                finalList = RequiredCountriesList.createFinalPaginatedCountriesList(populationGreaterThenList, endIndex, startIndex);// creating paginated result by using custom class RequiredCountriesList


            }

        }
        /*
        following if statement is to check if client do not wnt sorted data
        */
        if (withSort == null || withSort.equals(false)) {
            finalList = RequiredCountriesList.createFinalPaginatedCountriesList(populationGreaterThenList, endIndex, startIndex);
        }
        if (finalList.isEmpty()) {
            throw new CDException("No countries found");
        } else {
            return new ResponseEntity<>(finalList, HttpStatus.FOUND); // returning the finalList

        }
    }
    @GetMapping(value = "/population_less_then/{population}")
    public ResponseEntity<List<Map<String,Object>>> populationLessThenN(@PathVariable(name="population") Integer population,
                                                                        @RequestParam(name="page_size") Integer pageSize,
                                                                        @RequestParam(name="page_num") Integer pageNum,
                                                                        @RequestParam(name="sort",required = false) Boolean withSort,
                                                                        @RequestParam(name="sort_direction",required = false) String sortdirection) throws JsonProcessingException {
        List<Map<String,Object>> populationLessthenList=new ArrayList<>();
        List<Map<String,Object>> finalList=new ArrayList<>();

        final String uri="https://restcountries.com/v3.1/all";
        assert uri != null;
        ResponseEntity<String> jsonForAllCountries= restTemplate.getForEntity(uri, String.class);

        List<Map<String, Object>> dataList=objectMapper.readValue(jsonForAllCountries.getBody(),new TypeReference<>(){});

        Integer endIndex=pageNum*pageSize-1;
        Integer startIndex=endIndex-pageSize+1;

        for(Map<String,Object> jsonObj:dataList) {
            Object populationObj=jsonObj.get("population");
            Integer actualPopulation= (Integer) populationObj;
            if(actualPopulation<=population){
                populationLessthenList.add(jsonObj);
            }
        }
        if (withSort!=null&&withSort.equals(true)) {
            String direction=sortdirection.toLowerCase();
            RequiredCountriesList.sortCountriesPopulation(populationLessthenList);
            if(direction.equals("asc")){
                finalList= RequiredCountriesList.createFinalPaginatedCountriesList(populationLessthenList,endIndex,startIndex);
            }
            if(direction.equals("desc")){
                Collections.reverse(populationLessthenList);
                finalList= RequiredCountriesList.createFinalPaginatedCountriesList(populationLessthenList,endIndex,startIndex);

            }

        }
        if(withSort==null||withSort.equals(false)) {
            finalList = RequiredCountriesList.createFinalPaginatedCountriesList(populationLessthenList, endIndex, startIndex);
        }
        return new ResponseEntity<>(finalList, HttpStatus.FOUND);
    }
    @GetMapping(value = "/area_less_then/{area}")
    public ResponseEntity<List<Map<String,Object>>> areaLessThenN(@PathVariable(name="area") Integer area,
                                                                  @RequestParam(name="page_size") Integer pageSize,
                                                                  @RequestParam(name="page_num") Integer pageNum,
                                                                  @RequestParam(name="sort",required = false) Boolean withSort,
                                                                  @RequestParam(name="sort_direction",required = false) String sortdirection) throws JsonProcessingException {
        List<Map<String,Object>> areaLessthenList=new ArrayList<>();
        List<Map<String,Object>> finalList=new ArrayList<>();

        String uri="https://restcountries.com/v3.1/all";
        ResponseEntity<String> jsonForAllCountries= restTemplate.getForEntity(uri, String.class);

        List<Map<String, Object>> dataList=objectMapper.readValue(jsonForAllCountries.getBody(),new TypeReference<>(){});

        Integer endIndex=pageNum*pageSize-1;
        Integer startIndex=endIndex-pageSize+1;

        for(Map<String,Object> jsonObj:dataList) {
            Object areaObj=jsonObj.get("area");
            Double actualArea= (Double) areaObj;
            if(actualArea<=area){
                areaLessthenList.add(jsonObj);
            }
        }

        if (withSort!=null&&withSort.equals(true)) {
            String direction=sortdirection.toLowerCase();
            RequiredCountriesList.sortCountriesArea(areaLessthenList);
            if(direction.equals("asc")){
                finalList= RequiredCountriesList.createFinalPaginatedCountriesList(areaLessthenList,endIndex,startIndex);
            }
            if(direction.equals("desc")){
                Collections.reverse(areaLessthenList);
                finalList= RequiredCountriesList.createFinalPaginatedCountriesList(areaLessthenList,endIndex,startIndex);

            }

        }
        if(withSort==null||withSort.equals(false)) {
            finalList = RequiredCountriesList.createFinalPaginatedCountriesList(areaLessthenList, endIndex, startIndex);
        }
        return new ResponseEntity<>(finalList, HttpStatus.FOUND);
    }
    @GetMapping(value = "/area_greater_then/{area}")
    public ResponseEntity<List<Map<String,Object>>> areaGreaterThenN(@PathVariable(name="area") Integer area,
                                                                     @RequestParam(name="page_size") Integer pageSize,
                                                                     @RequestParam(name="page_num") Integer pageNum,
                                                                     @RequestParam(name="sort",required = false) Boolean withSort,
                                                                     @RequestParam(name="sort_direction",required = false) String sortdirection) throws JsonProcessingException {
        List<Map<String,Object>> areaMoreThenList=new ArrayList<>();
        List<Map<String,Object>> finalList=new ArrayList<>();

        String uri="https://restcountries.com/v3.1/all";
        ResponseEntity<String> jsonForAllCountries= restTemplate.getForEntity(uri, String.class);

        List<Map<String, Object>> dataList=objectMapper.readValue(jsonForAllCountries.getBody(),new TypeReference<>(){});

        Integer endIndex=pageNum*pageSize-1;
        Integer startIndex=endIndex-pageSize+1;

        for(Map<String,Object> jsonObj:dataList) {
            Object areaObj=jsonObj.get("area");
            Double actualArea= (Double) areaObj;
            if(actualArea>=area){
                areaMoreThenList.add(jsonObj);
            }
        }
        if (withSort!=null&&withSort.equals(true)) {
            String direction=sortdirection.toLowerCase();
            RequiredCountriesList.sortCountriesArea(areaMoreThenList);
            if(direction.equals("asc")){
                finalList= RequiredCountriesList.createFinalPaginatedCountriesList(areaMoreThenList,endIndex,startIndex);
            }
            if(direction.equals("desc")){
                Collections.reverse(areaMoreThenList);
                finalList= RequiredCountriesList.createFinalPaginatedCountriesList(areaMoreThenList,endIndex,startIndex);
            }

        }
        if(withSort==null||withSort.equals(false)) {
            finalList = RequiredCountriesList.createFinalPaginatedCountriesList(areaMoreThenList, endIndex, startIndex);
        }
        return new ResponseEntity<>(finalList, HttpStatus.FOUND);
    }
    /*
        1. Following method(countriesByLanguage) is used to get country details by language
        2. Here we are getting country name as path variable from client
        3. we are getting page-size and page-number from client as Request Parameter
        4. RestTemplate Object is being used to call the restcountries API
        5. I made a seperate class in Utility package for pagination
           whose method we are calling, returns a paginated result

     */
    @GetMapping("/get_countries_by_language/{language}")
    public ResponseEntity<List<Map<String,Object>>> countriesByLanguage(@PathVariable(name="language") String language,
                                                                        @RequestParam(name="page_size") Integer pageSize,
                                                                        @RequestParam(name="page_num") Integer pageNum) throws JsonProcessingException {

        Integer endIndex=pageNum*pageSize-1;
        Integer startIndex=endIndex-pageSize+1;

        String uri="https://restcountries.com/v3.1/lang/"+language;

        ResponseEntity<String> allCountriesJson=restTemplate.getForEntity(uri,String.class);

        List<Map<String,Object>> allCountries=objectMapper.readValue(allCountriesJson.getBody(),new TypeReference<>(){});

        List<Map<String,Object>> finalList= RequiredCountriesList.createFinalPaginatedCountriesList(allCountries,endIndex,startIndex);

        return new ResponseEntity<>(finalList,HttpStatus.FOUND);
    }

}

