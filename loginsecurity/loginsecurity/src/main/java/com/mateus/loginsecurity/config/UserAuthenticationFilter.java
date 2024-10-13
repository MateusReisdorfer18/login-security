package com.mateus.loginsecurity.config;

import com.mateus.loginsecurity.entity.User;
import com.mateus.loginsecurity.entity.UserDetailsImpl;
import com.mateus.loginsecurity.repository.UserRepository;
import com.mateus.loginsecurity.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {
     private final JwtTokenService jwtTokenService;
     private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(this.checkIsEndpointIsNotPublic(request)) {
            String token = this.recoveryToken(request);
            if(token == null)
                throw new RuntimeException("Token está ausente");

            String subject = this.jwtTokenService.getSubjectFromToken(token);
            User user = this.userRepository.findByEmail(subject).get();
            UserDetailsImpl userDetails = UserDetailsImpl.builder()
                    .user(user)
                    .build();

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader("Authorization");
        if(authenticationHeader == null || !authenticationHeader.startsWith("Bearer "))
            return null;

        return authenticationHeader.replace("Bearer ", "");
    }

    private boolean checkIsEndpointIsNotPublic(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestUri);
    }
}
