package com.tuan.identityservice.repository;

import com.tuan.identityservice.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken,String> {
    @Query(value = "select token from registration_token where registration_token.userid=:userid",nativeQuery = true)
    List<String> findTokenByUserId(@Param("userid") String userid);
}
