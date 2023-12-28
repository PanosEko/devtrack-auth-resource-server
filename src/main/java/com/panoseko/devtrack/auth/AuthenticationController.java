package com.panoseko.devtrack.auth;

import com.panoseko.devtrack.exception.InvalidJwtException;
import com.panoseko.devtrack.exception.UsernameAlreadyTakenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterRequest request,
            HttpServletResponse response
    ) throws UsernameAlreadyTakenException {
        AuthenticationResponse authResponse = authService.register(request);
        response.addCookie(authResponse.getAccessTokenCookie());
        response.addCookie(authResponse.getRefreshTokenCookie());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(
            @CookieValue(name = "refresh-token") String refreshToken,
            HttpServletResponse response) throws UsernameNotFoundException, InvalidJwtException {
        Cookie accessTokenCookie = authService.authenticateRefreshToken(refreshToken);
        response.addCookie(accessTokenCookie);
        return ResponseEntity.ok().build();

    }
}


