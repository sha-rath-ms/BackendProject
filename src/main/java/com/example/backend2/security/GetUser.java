package com.example.backend2.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Slf4j
public class GetUser {
    public String getId(HttpServletRequest request)
    {
        String username=null;
        String authHeader=request.getHeader(AUTHORIZATION);
        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            try {
                String token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                username = decodedJWT.getSubject();
            }
            catch (Exception e)
            {
                log.error("Error logging in:{}",e.getMessage());
            }
        }
        else
            throw new RuntimeException();
        return username;
    }
}
