package com.aliwert.jwt;

import com.aliwert.exception.BaseException;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component //for bean
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization"); // get auth
        if (header == null) {
            filterChain.doFilter(request, response);
        }
        String token;
        String username;
        token = header.substring(7); // Bearer

        try {
            //if it can solve token
            username = jwtService.getUsername(token);
            if (username == null || SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetails.loadUserByUsername(username); // check db
                if (userDetails != null && jwtService.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                    authentication.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication); // access controller
                }
            }
        } catch (ExpiredJwtException e) {
            throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPIRED, e.getMessage()));
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
        }
        filterChain.doFilter(request, response); // continue the process
    }
}