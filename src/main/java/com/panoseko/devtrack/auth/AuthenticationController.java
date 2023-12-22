package com.panoseko.devtrack.auth;

import com.panoseko.devtrack.exception.InvalidJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @RequestBody @Valid AuthenticationRequest request,
            HttpServletResponse response
    ) throws UsernameNotFoundException, AuthenticationException
    {
        AuthenticationResponse authResponse = authService.authenticateUser(request);
        response.addCookie(authResponse.getAccessTokenCookie());
        response.addCookie(authResponse.getRefreshTokenCookie());
        return ResponseEntity.ok("User authenticated successfully.");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse authResponse = authService.register(request);
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already exists.");
        }
        response.addCookie(authResponse.getAccessTokenCookie());
        response.addCookie(authResponse.getRefreshTokenCookie());
        return ResponseEntity.ok("User registered successfully.");
    }


    // TODO required = false?
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(
            @CookieValue(name = "refresh-token") String refreshToken,
            HttpServletResponse response) throws UsernameNotFoundException, InvalidJwtException {
        if (refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Refresh token not found.");
        }
        System.out.println("refresh token" + refreshToken);
        Cookie accessTokenCookie = authService.authenticateRefreshToken(refreshToken);
        response.addCookie(accessTokenCookie);
        return ResponseEntity.ok("Access token refreshed.");

    }
}


