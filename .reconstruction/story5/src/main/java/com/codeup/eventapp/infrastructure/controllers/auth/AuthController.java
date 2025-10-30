package com.codeup.eventapp.infrastructure.controllers.auth;
import com.codeup.eventapp.infrastructure.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    public AuthController(JwtUtil jwtUtil) { this.jwtUtil = jwtUtil; }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        if ("admin".equals(username) && "admin".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) { return ResponseEntity.ok("User registered successfully"); }
}
