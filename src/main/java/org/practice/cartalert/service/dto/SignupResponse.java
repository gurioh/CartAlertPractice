package org.practice.cartalert.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponse {
    private Long id;
    private String email;
    private String name;
    private String message;
}