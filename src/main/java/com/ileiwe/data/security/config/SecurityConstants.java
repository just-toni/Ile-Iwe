package com.ileiwe.data.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConstants {

    @Value("${secret}")
    public static String SECRET;
    @Value("${expiration.time}")
    public static long EXPIRATION_TIME; // 10days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
