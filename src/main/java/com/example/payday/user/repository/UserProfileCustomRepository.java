package com.example.payday.user.repository;

import com.example.payday.user.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserProfileCustomRepository {
    Page<UserProfile> findAllSorted(String sortKey, Pageable pageable);
}