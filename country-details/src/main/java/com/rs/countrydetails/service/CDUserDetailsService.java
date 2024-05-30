package com.rs.countrydetails.service;

import com.rs.countrydetails.dto.CDUserDetails;
import com.rs.countrydetails.repo.UserRepository;
import com.rs.countrydetails.utility.DTOEntityCoversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CDUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return DTOEntityCoversion.entityToDTO(userRepository.getUserByUsername(username).get());
    }
}
