package com.panoseko.devtrack.auth;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse  {

    private Cookie accessTokenCookie;
    private Cookie refreshTokenCookie;
}
