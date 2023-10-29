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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final TokenRepository tokenRepository;

    @Value("${access.token.cookie.name}")
    private String accessTokenCookieName;

    @Value("${refresh.token.cookie.name}")
    private String refreshTokenCookieName;

    @Value("${access.token.path}")
    private String accessTokenPath;

    @Value("${refresh.token.path}")
    private String refreshTokenPath;

    @Value("${cookie.domain}")
    private String cookieDomain;


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
        saveToken(user, refreshToken);
        Cookie accessTokenCookie = generateAccessCookie(accessToken);
        Cookie refreshTokenCookie = generateRefreshCookie(refreshToken);
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
        userRepository.save(user);
        Map<String, Object> customClaims = Map.of("uid", user.getId());
        var accessToken = jwtService.generateAccessToken(customClaims, user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveToken(user, refreshToken);
        Cookie accessTokenCookie = generateAccessCookie(accessToken);
        Cookie refreshTokenCookie = generateRefreshCookie(refreshToken);
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
            Map<String, Object> customClaims = Map.of("uid", user.getId());
            var accessToken = jwtService.generateAccessToken(customClaims, user);
            return generateAccessCookie(accessToken);
        }else {
            throw new BadCredentialsException("Invalid refresh token.");
        }
    }

    private void saveToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private Cookie generateAccessCookie(String accessToken) {
        return CookieUtils.generateCookie(
                accessTokenCookieName,
                accessToken,
                accessTokenPath,
                cookieDomain,
                15
        );
    }
    private Cookie generateRefreshCookie(String refreshToken) {
        return CookieUtils.generateCookie(
                refreshTokenCookieName,
                refreshToken,
                refreshTokenPath,
                cookieDomain,
                1440 * 5 // 5 days
        );
    }

}
