package com.example.payday.user.repository;

import com.example.payday.user.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, UserProfileCustomRepository {

    Optional<UserProfile> findWithUserByUserId(Long userId);



}