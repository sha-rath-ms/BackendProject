package com.example.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Data
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/login"))
            filterChain.doFilter(request,response);
        else
        {
            String authHeader=request.getHeader(AUTHORIZATION);
            if(authHeader!=null && authHeader.startsWith("Bearer "))
            {
                try {
                    String token = authHeader.substring("Bearer ".length());
                    String username=jwtTokenUtil.getUsernameFromToken(token);
                    if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
                    {
                        MyUserDetails myUserDetails=(MyUserDetails) myUserDetailsService.loadUserByUsername(username);
                        if(jwtTokenUtil.validateToken(token,myUserDetails)){
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null);
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                     }
                    filterChain.doFilter(request, response);
                }
                catch (Exception e)
                {
                    log.error("Error logging in:{}",e.getMessage());
                }
            }
            else
                filterChain.doFilter(request,response);
        }
    }
}
