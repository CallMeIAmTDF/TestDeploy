package com.huce.edu.security;

import com.huce.edu.entities.AdminsEntity;
import com.huce.edu.entities.KeytokenEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.repositories.AdminsRepo;
import com.huce.edu.repositories.KeyRepo;
import com.huce.edu.repositories.UserAccountRepo;
import com.huce.edu.utils.BearerTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private AdminsRepo adminsRepo;
    private UserInfoUserDetailsService userDetailsService;
    private KeyRepo keyRepo;
    private UserAccountRepo userAccountRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = BearerTokenUtil.getToken(request);
        String username = BearerTokenUtil.getUserName(request);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UserEntity user = userAccountRepo.findFirstByEmail(username);
            KeytokenEntity keyByUser;
            if (user == null){
                AdminsEntity admins = adminsRepo.findFirstByEmail(username);
                keyByUser = keyRepo.findFirstByAid(admins.getAid());
            } else {
                keyByUser = keyRepo.findFirstByUid(user.getUid());
            }

            if (jwtService.validateToken(token, keyByUser.getPublickey(), userDetails) ) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = BearerTokenUtil.getToken(request);
//        String username = BearerTokenUtil.getUserName(request);
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UserEntity user = userAccountRepo.findFirstByEmail(username);
//            KeytokenEntity keyByUser = keyRepo.findFirstByUid(user.getUid());
//            if (jwtService.validateToken(token, keyByUser.getPublickey(), userDetails) ) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
}
