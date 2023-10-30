package com.panoseko.devtrack.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Optional;

public final class CookieUtils {

    private CookieUtils(){}

    public static Cookie generateCookie(String name, String value, String path, String domain, int maxAgeMinutes) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(path);
        cookie.setDomain(domain);
        cookie.setMaxAge( (int) (System.currentTimeMillis() + 1000 * 60 * maxAgeMinutes) / 1000);
        return cookie;
    }

    public static Optional<String> readServletCookie(HttpServletRequest request, String name){
        return Arrays.stream(request.getCookies())
                .filter(cookie->name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
    }
}
