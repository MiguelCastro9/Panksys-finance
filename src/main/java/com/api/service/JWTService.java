package com.api.service;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Miguel Castro
 */
public interface JWTService {
    
    String getUsername(String token);
    
    UserDetailsService userDetailsService();
    
    String generateToken(UserDetails userDetails);
    
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
    
    boolean isTokenValid(String token, UserDetails userDatails);
}
