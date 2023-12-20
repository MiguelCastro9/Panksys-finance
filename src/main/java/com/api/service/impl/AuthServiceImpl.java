package com.api.service.impl;

import com.api.dto.request.SigninRequestDto;
import com.api.dto.request.RefreshTokenRequestDto;
import com.api.dto.response.JWTResponseDto;
import com.api.model.UserModel;
import com.api.repository.UserRepository;
import com.api.service.JWTService;
import java.util.HashMap;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author Miguel Castro
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Override
    public JWTResponseDto signin(SigninRequestDto signinRequestDto) {
        UserModel user = userRepository.findByEmail(signinRequestDto.getEmail()).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        if (user.isEnabled() == false) {
            throw new IllegalArgumentException("User not is enabled.");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequestDto.getEmail(), signinRequestDto.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Invalid e-mail or password.");
        }
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JWTResponseDto jwtAuthenticationResponseDto = new JWTResponseDto();
        jwtAuthenticationResponseDto.setToken(jwt);
        jwtAuthenticationResponseDto.setRefresh_token(refreshToken);
        return jwtAuthenticationResponseDto;
    }

    @Override
    public JWTResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        String email = jwtService.getUsername(refreshTokenRequestDto.getToken());
        UserModel userModel = userRepository.findByEmail(email).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequestDto.getToken(), userModel)) {
            var jwt = jwtService.generateToken(userModel);
            JWTResponseDto jwtAuthenticationResponseDto = new JWTResponseDto();
            jwtAuthenticationResponseDto.setToken(jwt);
            jwtAuthenticationResponseDto.setRefresh_token(refreshTokenRequestDto.getToken());
            return jwtAuthenticationResponseDto;
        }
        return null;
    }
}
