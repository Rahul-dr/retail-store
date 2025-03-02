// package com.project.retail_store.configuration;
//
// import com.project.retail_store.security.JwtAuthenticationFilter;
// import com.project.retail_store.util.JwtUtil;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
// import java.util.Arrays;
// import java.util.List;
//
// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
// public class SecurityConfig {
//
//    private final JwtUtil jwtUtil;
//    private final UserDetailsService userDetailsService;
//
//    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.csrf(AbstractHttpConfigurer::disable)
////                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
////                .authorizeHttpRequests(
////                        auth -> auth.requestMatchers("/api/admin/login").permitAll()
////                                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
////                                .requestMatchers("/api/v1/**").hasAnyAuthority("ROLE_CUSTOMER",
// "ROLE_ADMIN")
////                                .anyRequest()
////                                .authenticated())
////                .sessionManagement(
////                        session ->
// session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService),
// UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService
// userDetailsService) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(
//                        auth -> auth
//                                .requestMatchers("/api/admin/login").permitAll()
//                                .requestMatchers("/api/v1/auth/login").permitAll()
//                                .requestMatchers("/api/v1/auth/register").permitAll()
//                                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
//                                .requestMatchers("/api/v1/**").hasAnyAuthority("ROLE_CUSTOMER",
// "ROLE_ADMIN")
//                                .anyRequest().authenticated()
//                )
//                .sessionManagement(session ->
// session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService),
// UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3000",
// "https://yourdomain.com"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type",
// "x-auth-token"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
// }

package com.project.retail_store.configuration;

import com.project.retail_store.entity.User;
import com.project.retail_store.repository.UserRepository;
import com.project.retail_store.security.JwtAuthenticationFilter;
import com.project.retail_store.util.JwtUtil;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtUtil jwtUtil;

  public SecurityConfig(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return email -> {
      User user =
          userRepository
              .findByEmail(email)
              .orElseThrow(
                  () -> new UsernameNotFoundException("User not found with email: " + email));

      return new org.springframework.security.core.userdetails.User(
          user.getEmail(),
          user.getPassword(),
          List.of(new SimpleGrantedAuthority(user.getRole().name())) // Convert role to authority
          );
    };
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/admin/login")
                    .permitAll()
                    .requestMatchers("/api/v1/auth/login")
                    .permitAll()
                    .requestMatchers("/api/v1/auth/register")
                    .permitAll()
                    .requestMatchers("/api/admin/**")
                    .hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/api/v1/**")
                    .hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(
            new JwtAuthenticationFilter(jwtUtil, userDetailsService),
            UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://yourdomain.com"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
