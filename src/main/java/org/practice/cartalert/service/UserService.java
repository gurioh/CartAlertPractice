package org.practice.cartalert.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.practice.cartalert.repository.entity.User;
import org.practice.cartalert.global.exception.DuplicateEmailException;
import org.practice.cartalert.repository.UserRepository;
import org.practice.cartalert.service.dto.SignupRequest;
import org.practice.cartalert.service.dto.SignupResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 사용자 엔티티 생성 및 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .role("ROLE_USER")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        // 응답 생성
        return SignupResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .message("회원가입이 성공적으로 완료되었습니다.")
                .build();
    }
}