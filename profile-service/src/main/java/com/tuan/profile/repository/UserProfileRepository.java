package com.tuan.profile.repository;

import com.tuan.profile.dto.userprofileDto.UserProfileResponse;
import org.springframework.data.domain.Example;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.tuan.profile.entity.UserProfile;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {
    UserProfile findByFirstNameAndLastNameAndCitizenIdCard(String firstName, String lastName, String citizenIdCard);
    boolean existsByCitizenIdCard(String citizenCardId);

    boolean existsByEmail(String email);
    UserProfile findByUserId(String userId);
}
