package com.ajackus.quiz.controller;
import com.ajackus.quiz.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        try {
            var auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.get("username"), request.get("password")
                    )
            );

            String role = auth.getAuthorities().iterator().next().getAuthority();

            String token = jwtUtil.generateToken(request.get("username"), role);

            return Map.of("token", token);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid login");
        }
    }
}

