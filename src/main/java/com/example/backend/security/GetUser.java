package com.example.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Slf4j
@Data
public class GetUser {
    private final JwtTokenUtil jwtTokenUtil;
    public Long getId(HttpServletRequest request)
    {
        String username=null;
        String authHeader=request.getHeader(AUTHORIZATION);
        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            try {
                String token = authHeader.substring("Bearer ".length());
                username = jwtTokenUtil.getUsernameFromToken(token);
            }
            catch (Exception e)
            {
                log.error("Error logging in:{}",e.getMessage());
            }
        }
        else
            throw new RuntimeException();
        return Long.parseLong(username);
    }
}
