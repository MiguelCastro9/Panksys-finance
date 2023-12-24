package com.api.service;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Miguel Castro
 */
public interface JWTService {
    
    public String getUsername(String token);
    
    public UserDetailsService userDetailsService();
    
    public String generateToken(UserDetails userDetails);
    
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
    
    public boolean isTokenValid(String token, UserDetails userDatails);
}
