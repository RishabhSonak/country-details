package com.rs.countrydetails.repo;

import com.rs.countrydetails.entity.CDUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<CDUser,String> {
    @Query("SELECT u FROM CDUser u WHERE u.username=:username")
    public Optional<CDUser> getUserByUsername(@Param("username") String username);
}
