package com.panoseko.devtrack.auth;

import com.panoseko.devtrack.exception.UsernameAlreadyExistsException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ){
        AuthenticationResponse authResponse = authService.authenticateUser(request);
        response.addCookie(authResponse.getAccessTokenCookie());
        response.addCookie(authResponse.getRefreshTokenCookie());
        return ResponseEntity.ok("User authenticated successfully.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse authResponse = authService.register(request);
        response.addCookie(authResponse.getAccessTokenCookie());
        response.addCookie(authResponse.getRefreshTokenCookie());
        return ResponseEntity.ok("User registered successfully.");
    }


    // TODO required = false?
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @CookieValue(name = "refresh-token", required = false) String refreshToken,
            HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Refresh token not found.");
        } else {
            try{
                Cookie accessTokenCookie = authService.authenticateRefreshToken(refreshToken);
                response.addCookie(accessTokenCookie);
                return ResponseEntity.ok("Access token authenticated successfully.");
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid refresh token.");
            }
        }
    }
}


