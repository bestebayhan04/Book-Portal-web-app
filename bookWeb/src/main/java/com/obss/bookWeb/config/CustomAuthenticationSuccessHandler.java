package com.obss.bookWeb.config;


import java.io.IOException;
import java.util.*;

import javax.crypto.SecretKey;

import com.obss.bookWeb.model.FavList;
import com.obss.bookWeb.model.ReadList;
import com.obss.bookWeb.repository.FavListRepo;
import com.obss.bookWeb.repository.ReadListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private FavListRepo favListRepo;

    @Autowired
    private ReadListRepo readListRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

        String email = authentication.getName();
        String userRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse("ROLE_USER");

        Optional<String> userIdAuthority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_USER_ID_"))
                .findFirst();

        Integer userId = null;
        String favListId = null;
        String readListId = null;

        if (userIdAuthority.isPresent()) {
            userId = Integer.parseInt(userIdAuthority.get().replace("ROLE_USER_ID_", ""));
            System.out.println("user id is: " + userId);

            try {
                FavList favList = favListRepo.findByUser_UserId(userId);
                if (favList != null) {
                    favListId = favList.getFavlistId().toString();
                    System.out.println("Cart ID is: " + favListId);
                }
            } catch (Exception e) {
                System.err.println("Error retrieving cart for user ID " + userId + ": " + e.getMessage());
            }

            try {
                ReadList readList = readListRepo.findByUser_UserId(userId);
                if (readList != null) {
                    readListId = readList.getReadlistId().toString();
                    System.out.println("Cart ID is: " + readListId);
                }
            } catch (Exception e) {
                System.err.println("Error retrieving cart for user ID " + userId + ": " + e.getMessage());
            }
        }

        String jwt = Jwts.builder()
                .setIssuer("e-commerce")
                .setSubject("JWT Token")
                .claim("email", email)
                .claim("role", userRole)
                .claim("userId", userId)
                .claim("favlistId", favListId)
                .claim("readlistId", readListId)
                .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 30000000))
                .signWith(key).compact();

        response.setHeader("Authorization", "Bearer " + jwt);
        System.out.println("JWT Generated and added to response header: " + jwt);

    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}