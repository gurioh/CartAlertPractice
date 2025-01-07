package org.practice.cartalert.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.practice.cartalert.repository.entity.User;
import org.practice.cartalert.repository.UserAuthTokensRepository;
import org.practice.cartalert.repository.UserRepository;
import org.practice.cartalert.service.dto.LogoutResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthTokenService {

    private final UserRepository userRepository;
    private final UserAuthTokensRepository userAuthTokensRepository;

    @Transactional
    public LogoutResponse removeUserAuthTokens(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        userAuthTokensRepository.deleteByUserId(user.getId());

        return LogoutResponse.builder()
                .resultYn("Y")
                .userInfo(LogoutResponse.UserInfo.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .build())
                .build();
    }

}
