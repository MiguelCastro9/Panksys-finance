package com.api.service;

import com.api.dto.request.SigninRequestDto;
import com.api.dto.request.RefreshTokenRequestDto;
import com.api.dto.response.JWTResponseDto;

/**
 *
 * @author Miguel Castro
 */
public interface AuthService {
    
    public JWTResponseDto signin(SigninRequestDto loginRequestDto);
    
    public JWTResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
}
