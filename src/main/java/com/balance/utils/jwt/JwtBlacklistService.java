package com.balance.utils.jwt;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtBlacklistService {

    // Map to store blacklisted tokens and their expiration times
    private final Map<String, Instant> tokenBlacklist = new ConcurrentHashMap<>();

    // Blacklist a token until its expiration
    public void blacklistToken(String token, Instant expiration) {
        tokenBlacklist.put(token, expiration);
    }

    // Check if the token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        Instant expiration = tokenBlacklist.get(token);
        if (expiration == null) {
            return false; // Token not in blacklist
        }

        // Remove the token if it's already expired
        if (Instant.now().isAfter(expiration)) {
            tokenBlacklist.remove(token);
            return false;
        }

        return true; // Token is blacklisted and still valid
    }
}