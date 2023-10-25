package com.panoseko.devtrack.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
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
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class AuthenticationResponse {
//    private HttpServletResponse response;
//    private String message;
//    @JsonProperty("access_token")
//    private String accessToken;
//
//    public AuthenticationResponse(HttpServletResponse response) {
//        this.response = response;
//    }
//
//    public void addCookie(String token){
//        Cookie cookie = new Cookie("token", token);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/api/v1/auth");
//        cookie.setDomain("localhost");
//        cookie.setMaxAge((int)(System.currentTimeMillis()+1000*60*1440*5)/1000); // 5 days
//        response.addCookie(cookie);
//    }
//
//
//    // Delegate other methods to the original response object
//    public int getStatus() {
//        return response.getStatus();
//    }
//
//}