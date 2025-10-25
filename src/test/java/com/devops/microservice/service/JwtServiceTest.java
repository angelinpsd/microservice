package com.devops.microservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken();

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testGenerateUniqueTokens() {
        String token1 = jwtService.generateToken();
        String token2 = jwtService.generateToken();

        assertNotEquals(token1, token2);
    }
}