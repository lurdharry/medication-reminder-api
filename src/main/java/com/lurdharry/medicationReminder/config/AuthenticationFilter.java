package com.lurdharry.medicationReminder.config;

import com.lurdharry.medicationReminder.exception.ResponseException;
import com.lurdharry.medicationReminder.user.UserRepository;
import com.lurdharry.medicationReminder.user.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        String token = "";
        String email = "";

        String header = request.getHeader("Authorization");

        if  (header != null && header.startsWith("Bearer ")) {
             token = header.substring(7);
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var isTokenExpired = jwtUtility.isTokenExpired(token);
            email = jwtUtility.extractEmailFromToken(token);
            if (email == null || isTokenExpired) {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new ResponseException("Invalid email", HttpStatus.NOT_FOUND)
        );
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null, Collections.emptyList());
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
