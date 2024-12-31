package org.practice.cartalert.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "user_auth_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "token_type", nullable = false)
    private String tokenType;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;


    public UserAuthToken(Long userId, String accessToken, String refreshToken, String tokenType, LocalDateTime expiresAt) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
        this.setCreatedAtNow();
    }

    public UserAuthToken(Long userId, String accessToken, String refreshToken, String tokenType, Long expiretimeLong) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(expiretimeLong), ZoneId.systemDefault());
        this.setCreatedAtNow();
    }

    public void setCreatedAtNow(){
        this.createdAt = LocalDateTime.now();
    }


    public static UserAuthToken create(
            Long userId, String accessToken, String refreshToken, String tokenType, Long expiretimeLong){
        UserAuthToken userAuthToken = new UserAuthToken(userId, accessToken, refreshToken, tokenType, expiretimeLong);
        return userAuthToken;
    }
}
