package com.example.payday.user.repository;

import com.example.payday.user.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, UserProfileCustomRepository {

    Optional<UserProfile> findByUser_Id(Long userId);
    Optional<UserProfile> findByUserId(Long userId);


}