package com.aliwert.jwt;

import com.aliwert.config.SecurityConfig;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetails;
    
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    // List of paths that should be permitted without authentication
    private final List<String> permitAllPaths = Arrays.asList(
        SecurityConfig.REGISTER,
        SecurityConfig.AUTHENTICATE, 
        SecurityConfig.REFRESH_TOKEN
    );
    
    private final List<String> swaggerPaths = Arrays.asList(
        "/swagger-ui/**", 
        "/v3/api-docs/**",
        "/swagger-ui.html"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        // skip filter for permitted paths
        String requestPath = request.getServletPath();
        
        // check if path is in permitAllPaths or matches swagger patterns
        if (permitAllPaths.contains(requestPath) || 
                swaggerPaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestPath))) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Get Authorization header
        final String header = request.getHeader("Authorization");
        
        // If no auth header is present, continue to next filter
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Extract token
        final String token = header.substring(7);
        String username;
        
        try {
            // Extract username from token
            username = jwtService.getUsername(token);
            
            // If username exists and no authentication is set in security context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetails.loadUserByUsername(username);
                
                // If token is valid, set authentication in security context
                if (jwtService.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    authentication.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException e) {
            throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPIRED, e.getMessage()));
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
        }
        
        filterChain.doFilter(request, response);
    }
}