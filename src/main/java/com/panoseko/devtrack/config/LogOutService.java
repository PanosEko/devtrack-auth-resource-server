package com.panoseko.devtrack.config;

import com.panoseko.devtrack.auth.CookieUtils;
import com.panoseko.devtrack.token.Token;
import com.panoseko.devtrack.token.TokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;


@Service
@RequiredArgsConstructor
public class LogOutService implements LogoutHandler {

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

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String refreshToken = CookieUtils.readServletCookie(request, "refresh-token").orElse(null);
        var storedToken = tokenRepository.findByToken(refreshToken).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
        SecurityContextHolder.clearContext();

        Cookie deleteAccessCookie = CookieUtils.generateCookie(
                accessTokenCookieName,
                null,
                accessTokenPath,
                cookieDomain,
                0);
        Cookie deleteRefreshCookie = CookieUtils.generateCookie(
                refreshTokenCookieName,
                null,
                refreshTokenPath,
                cookieDomain,
                0); // 5 days
        response.addCookie(deleteRefreshCookie);
        response.addCookie(deleteAccessCookie);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



