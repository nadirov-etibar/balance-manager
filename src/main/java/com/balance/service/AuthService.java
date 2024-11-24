package com.balance.service;

import com.balance.entity.User;
import com.balance.exception.HandleHttpErrors;
import com.balance.repository.UserRepository;
import com.balance.request.user.UserRegistrationDTO;
import com.balance.utils.jwt.JwtBlacklistService;
import com.balance.utils.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.Instant;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User registrationUser(UserRegistrationDTO request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new HandleHttpErrors("Validation errors occurred", bindingResult);
        } else if (userRepository.existsByUsername(request.getUsername())) {
            throw new HandleHttpErrors("Username already exist");
        } else {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            return userRepository.save(user);
        }
    }

    public String loginUser(UserRegistrationDTO request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new HandleHttpErrors("Validation errors occurred", bindingResult);
        }

        User user = userRepository.getByUsername(request.getUsername());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new HandleHttpErrors("Invalid email or password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        return token;
    }

    public String logoutUser(HttpServletRequest request){
        String token = jwtUtil.extractToken(request);

        Instant expiration = jwtUtil.getExpirationFromToken(token);

        jwtBlacklistService.blacklistToken(token, expiration);

        return token;
    }
}
