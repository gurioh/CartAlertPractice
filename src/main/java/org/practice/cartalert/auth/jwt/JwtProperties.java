package org.practice.cartalert.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;


@ConfigurationPropertiesScan(value = "spring.security.jwt")
@Getter
@Setter
public class JwtProperties {

    @Value("${spring.security.jwt.secret-key}")
    String secretKey;
    @Value("${spring.security.jwt.access-token-validity}")
    long accessTokenValidity;
    @Value("${spring.security.jwt.refresh-token-validity}")
    long refreshTokenValidity;
}
