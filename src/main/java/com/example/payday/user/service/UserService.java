package com.example.payday.user.service;

import com.example.payday.user.domain.User;
import com.example.payday.user.domain.UserProfile;
import com.example.payday.user.dto.UserSignupRequestDto;
import com.example.payday.user.dto.UserSignupResponseDto;
import com.example.payday.user.mapper.UserMapper;
import com.example.payday.user.repository.UserProfileRepository;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto dto) {
        User user = UserMapper.toEntity(dto);
        userRepository.save(user);

        UserProfile profile = UserMapper.toProfile(user, dto.getNickname());
        userProfileRepository.save(profile);

        return UserMapper.toSignupDto(user, profile);
    }
}
