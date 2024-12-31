package org.practice.cartalert.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogoutResponse {
    private String resultYn;
    private UserInfo userInfo;

    @Getter
    @Builder
    public static class UserInfo {
        private Long id;
        private String email;
        private String name;
    }
}