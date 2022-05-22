package io.swagger.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest); // get token from header
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // check if token is valid
                Authentication auth = jwtTokenProvider.getAuthentication(token); // get user from token
                SecurityContextHolder.getContext().setAuthentication(auth); // set user in security context
            }
        } catch (ResponseStatusException ex) {
            // if token is not valid
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(ex.getStatus().value(), ex.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse); // continue filter chain
    }
}
