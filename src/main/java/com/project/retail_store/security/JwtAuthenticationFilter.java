package com.project.retail_store.security;

import com.project.retail_store.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String authorizationHeader = request.getHeader("Authorization");

      String username = null;
      String jwt = null;
      String role = null;

      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        jwt = authorizationHeader.substring(7);
        username = jwtUtil.extractUsername(jwt);
        role = jwtUtil.extractRole(jwt); // Extract role
      }

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        if (userDetailsService != null) {
          UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

          if (jwtUtil.validateToken(jwt, userDetails)) {
            // Use extracted role for authorities
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        } else {
          logger.error("UserDetailsService is not initialized in JwtAuthenticationFilter");
          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          response.getWriter().write("UserDetailsService is not initialized");
        }
      }
    } catch (ExpiredJwtException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("JWT Token has expired");
    } catch (JwtException e) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.getWriter().write("Invalid JWT Token");
    }

    filterChain.doFilter(request, response);
  }
}
