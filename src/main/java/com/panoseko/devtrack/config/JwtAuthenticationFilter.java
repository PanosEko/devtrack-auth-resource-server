package com.panoseko.devtrack.config;

import com.panoseko.devtrack.auth.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//@Component
//@CrossOrigin(origins = "http://localhost:3000")
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain
//    ) throws ServletException, IOException {
//        if (request.getServletPath().contains("/api/v1/auth")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String jwtToken = jwtService.extractTokenFromCookie(request);
//        if (jwtToken == null ){
//            response.setStatus(HttpStatus.SEE_OTHER.value());
//            response.setHeader("Location", "http://localhost:3000");
//            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Location");
//            return;
//        }
//        String username = jwtService.extractUsername(jwtToken);
//        if (username !=null && SecurityContextHolder.getContext().getAuthentication()==null){
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//            if(jwtService.isTokenValid(jwtToken, userDetails)){
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities()
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//
//            }else{
//                response.setStatus(HttpStatus.SEE_OTHER.value());
//                response.setHeader("Location", "http://localhost:3000");
//                response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Location");
//            }
//        }
//        filterChain.doFilter(request, response);
//
//    }
//
//}

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = CookieUtils.readServletCookie(request, "access-token").orElse(null);
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String username = jwtService.extractUsername(jwtToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isAccessTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

