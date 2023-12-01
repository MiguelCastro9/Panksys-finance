package com.api.service;

import com.api.dto.request.SigninRequestDto;
import com.api.dto.request.RefreshTokenRequestDto;
import com.api.dto.response.JWTResponseDto;

/**
 *
 * @author Miguel Castro
 */
public interface AuthService {
    
    JWTResponseDto signin(SigninRequestDto loginRequestDto);
    
    JWTResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
}
