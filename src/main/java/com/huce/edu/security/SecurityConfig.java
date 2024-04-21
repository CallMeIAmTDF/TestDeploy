package com.huce.edu.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults())
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
////                .cors()
                .authorizeHttpRequests((authorize) -> authorize
//                                .requestMatchers("/edu-api-docs/**",
//                                        "/edu-documentation/**",
//                                        "/swagger-ui/**").permitAll()
                                .requestMatchers("/api/admin/sign-in",
                                        "/api/admin/verifyRefreshToken").permitAll()
                                .requestMatchers("/api/users/sign-in",
                                        "/api/users/verifyRefreshToken", "/api/users/forgetPassword/**").permitAll()
                                .requestMatchers("/api/admin/add").hasAuthority("SUPER_ADMIN")
                                .requestMatchers("/api/admin/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")

                                .requestMatchers("/api/order/getAllOrder").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                                .requestMatchers("/api/coin/**").authenticated()
                                .requestMatchers("/api/order/**").authenticated()
                                .requestMatchers("/api/history/**").authenticated()

                                .requestMatchers("/api/product/getAll").permitAll()
                                .requestMatchers("/api/product/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")

                                .requestMatchers("/api/level/getAll").permitAll()
                                .requestMatchers("/api/level/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")

                                .requestMatchers("/api/topic/getTopicByLid").permitAll()
                                .requestMatchers("/api/topic/getTopicByTid").permitAll()

                                .requestMatchers("/api/topic/getAllTopic").permitAll()
                                .requestMatchers("/api/topic/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")

                                .requestMatchers("/api/words/getAll").permitAll()
                                .requestMatchers("/api/words/getScrambleWord").permitAll()
                                .requestMatchers("/api/words/getQuestionByTid").authenticated()
                                .requestMatchers("/api/words/getTest").authenticated()

                                .requestMatchers("/api/words/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")

                                .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
