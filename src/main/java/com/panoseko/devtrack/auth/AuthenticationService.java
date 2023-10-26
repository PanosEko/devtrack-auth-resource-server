package com.panoseko.devtrack.auth;

import com.panoseko.devtrack.token.Token;
import com.panoseko.devtrack.token.TokenRepository;
import com.panoseko.devtrack.token.TokenType;
import com.panoseko.devtrack.config.JwtService;
import com.panoseko.devtrack.user.User;
import com.panoseko.devtrack.user.Role;
import com.panoseko.devtrack.user.UserRepository;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;


    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
        var user= userRepository.findMemberByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Map<String, Object> customClaims = Map.of("uid", user.getId());
        var accessToken = jwtService.generateAccessToken(customClaims, user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, refreshToken);
        Cookie accessTokenCookie = CookieUtils.generateCookie(
                "access-token",
                accessToken,
                "/api/v1/task",
                "devtrack-backend.onrender.com",
                15);
        Cookie refreshTokenCookie = CookieUtils.generateCookie(
                "refresh-token",
                refreshToken,
                "/api/v1/auth",
                "devtrack-backend.onrender.com",
                1440 * 5); // 5 days
        return new AuthenticationResponse(
                accessTokenCookie,
                refreshTokenCookie
        );
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findMemberByUsername(request.getUsername()).isPresent()) {
            throw new BadCredentialsException("Username is taken.");
        }
        var user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        Map<String, Object> customClaims = Map.of("uid", user.getId());
        var accessToken = jwtService.generateAccessToken(customClaims, user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, refreshToken);
        Cookie accessTokenCookie = CookieUtils.generateCookie(
                "access-token",
                accessToken,
                "/api/v1/task",
                "devtrack-backend.onrender.com",
                15);
        Cookie refreshTokenCookie = CookieUtils.generateCookie(
                "refresh-token",
                refreshToken,
                "/api/v1/auth",
                "devtrack-backend.onrender.com",
                1440 * 5); // 5 days
        return new AuthenticationResponse(
                accessTokenCookie,
                refreshTokenCookie
        );
    }

    public Cookie authenticateRefreshToken(String token) {
        String username = jwtService.extractUsername(token);
        var user= userRepository.findMemberByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        if (jwtService.isRefreshTokenValid(token, user)) {
            System.out.println("Refresh Token valid");
            Map<String, Object> customClaims = Map.of("uid", user.getId());
            var accessToken = jwtService.generateAccessToken(customClaims, user);
            return CookieUtils.generateCookie(
                    "access-token",
                    accessToken,
                    "/api/v1/task",
                    "devtrack-backend.onrender.com",
                    15);
        }else {
            throw new BadCredentialsException("Invalid refresh token.");
        }
    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

//
//    public String authenticateRefreshToken(String token) {
//        String username = jwtService.extractUsername(token);
//        var user= userRepository.findMemberByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        if (jwtService.isRefreshTokenValid(token, userDetails)) {
//            return generateAccessToken(user);
//        }else{
//            throw new BadCredentialsException("Invalid refresh token.");
//        }
//    }


}
