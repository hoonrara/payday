package com.example.payday.user.service;

import com.example.payday.user.domain.User;
import com.example.payday.user.domain.UserProfile;
import com.example.payday.user.dto.UserSignupRequestDto;
import com.example.payday.user.dto.UserSignupResponseDto;
import com.example.payday.user.exception.DuplicateEmailException;
import com.example.payday.user.mapper.UserMapper;
import com.example.payday.user.repository.UserProfileRepository;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException(); // 사전 중복 검사
        }

        try {
            User user = userRepository.save(UserMapper.toEntity(dto));
            UserProfile profile = userProfileRepository.save(UserMapper.toProfile(user, dto.getNickname()));
            return UserMapper.toSignupDto(user, profile);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException(); // 동시 가입 시 DB에서 막히는 경우
        }
    }
}
