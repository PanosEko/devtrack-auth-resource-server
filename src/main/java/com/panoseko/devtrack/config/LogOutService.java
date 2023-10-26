package com.panoseko.devtrack.config;

import com.panoseko.devtrack.auth.AuthenticationService;
import com.panoseko.devtrack.auth.CookieUtils;
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

@Service
@RequiredArgsConstructor
public class LogOutService implements LogoutHandler {

    private final TokenRepository tokenRepository;



    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
//        String cookieHeader = request.getHeader("Cookie");
//        String jwtToken = jwtService.extractTokenFromCookie(cookieHeader);
        String refreshToken = CookieUtils.readServletCookie(request, "refresh-token").orElse(null);//        String jwtToken = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            System.out.println("Logout Cookies found");
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    jwtToken = cookie.getValue();
//                    System.out.println("Logout Cookie found/n");
//                    break;
//                }
//            }
//        }

        var storedToken = tokenRepository.findByToken(refreshToken)
                .orElse(null);
        if (storedToken != null) {
            System.out.println("Stored token found");
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }

        // TODO send new cookie to delete old one
        Cookie deleteCookie = CookieUtils.generateCookie(
                "access-token",
                null,
                "/api/v1/task",
                "https://devtrack-backend.onrender.com",
                0); // 5 days

//        // Create a new cookie with the expired token
//        Cookie newCookie = createTokenCookie(storedToken.getToken());
//
//        // Invalidate the existing cookie
//        Cookie existingCookie = new Cookie("token", "");
//        existingCookie.setMaxAge(0);
//        existingCookie.setPath("/");
//        existingCookie.setDomain("localhost");
//
//        // Add the new cookie to the response
//        response.addCookie(newCookie);
        response.addCookie(deleteCookie);

    // Send response indicating logout success
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



