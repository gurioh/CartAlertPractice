package org.practice.cartalert.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.practice.cartalert.auth.jwt.JwtTokenProvider;
import org.practice.cartalert.repository.entity.User;
import org.practice.cartalert.repository.entity.UserAuthToken;
import org.practice.cartalert.repository.UserAuthTokensRepository;
import org.practice.cartalert.repository.UserRepository;
import org.practice.cartalert.service.dto.LoginRequest;
import org.practice.cartalert.service.dto.LoginResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserAuthTokensRepository userAuthTokensRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        // 1. 이메일로 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("이메일 또는 비밀번호가 일치하지 않습니다."));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // 3. 로그인 시간 업데이트
        user.updateLastLoginAt();

        // 4. JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        //ToDo: token save
        UserAuthToken userAuthToken = UserAuthToken.create(user.getId()
                , accessToken
                , refreshToken
                , "jwt"
                , jwtTokenProvider.getExpiration(accessToken));

        userAuthTokensRepository.save(userAuthToken);

        // 5. 응답 생성
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userInfo(LoginResponse.UserInfo.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .build())
                .build();
    }
}
