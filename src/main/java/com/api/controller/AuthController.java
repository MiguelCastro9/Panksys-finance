package com.api.controller;

import com.api.dto.request.SigninRequestDto;
import com.api.dto.request.RefreshTokenRequestDto;
import com.api.dto.request.UserRequestDto;
import com.api.dto.response.JWTResponseDto;
import com.api.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.service.AuthService;
import com.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Miguel Castro
 */
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authenticationService;

    @Autowired
    UserService userService;

    @PostMapping("/singup")
    public ResponseEntity<?> singup(@Valid @RequestBody UserRequestDto userRequestDto) {
        if (!userRequestDto.getPassword().equals(userRequestDto.getPasswordRepeated())) {
            return new ResponseEntity<>("Passwords is not equals.", HttpStatus.CONFLICT);
        }
        UserModel userBuilder = userService.singup(userRequestDto.convertUserDtoForEntity());
        return new ResponseEntity<>(userBuilder, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponseDto> signin(@RequestBody SigninRequestDto loginRequestDto) {
        return new ResponseEntity<>(authenticationService.signin(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return new ResponseEntity<>(authenticationService.refreshToken(refreshTokenRequestDto), HttpStatus.OK);
    }
}
