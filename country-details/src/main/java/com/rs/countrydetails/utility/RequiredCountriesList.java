package com.rs.countrydetails.utility;

import java.util.*;

public class RequiredCountriesList {
public static List<Map<String,Object>> createFinalPaginatedCountriesList(List<Map<String,Object>> requiredList,
                                         Integer endIndex,
                                         Integer startIndex){

    List<Map<String,Object>> finalList=new ArrayList<>();
    if(requiredList.size()>endIndex) {
        for (int i=startIndex;i<=endIndex;i++) {
            finalList.add(requiredList.get(i));
        }

    }
    if(requiredList.size()<=endIndex) {
        if(requiredList.size()>startIndex){
            for (int j=startIndex;j<requiredList.size();j++) {
                finalList.add(requiredList.get(j));
            }
        }

    }
    return finalList;
}

    public static void sortCountriesPopulation(List<Map<String, Object>> requiredList) {
        Collections.sort(requiredList, Comparator.comparing(m -> (Integer) m.get("population")));

    }
    public static void sortCountriesArea(List<Map<String, Object>> requiredList) {
        Collections.sort(requiredList, Comparator.comparing(m -> (Double) m.get("area")));

    }

}
