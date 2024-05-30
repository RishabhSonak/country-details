package com.rs.countrydetails.utility;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rs.countrydetails.dto.CDUserDetails;
import com.rs.countrydetails.entity.CDUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class DTOEntityCoversion {

    public static CDUser DTOtoEntity(CDUserDetails userDetails){
        CDUser cdUser =new CDUser();
        cdUser.setUsername(userDetails.getUsername());
        cdUser.setPassword(userDetails.getPassword());
        cdUser.setIsAccountNonLocked(userDetails.isAccountNonLocked());
        cdUser.setIsAccountNonExpired(userDetails.isAccountNonExpired());
        cdUser.setAuthorities(cdUser.getAuthorities());
        return cdUser;
    }
    public static CDUserDetails entityToDTO(CDUser user){
        CDUserDetails userDetails =new CDUserDetails();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setAccountNonLocked(user.getIsAccountNonLocked());
        userDetails.setAccountNonExpired(user.getIsAccountNonExpired());
        userDetails.setAuthorities(user.getAuthorities());
        return userDetails;
    }

}
