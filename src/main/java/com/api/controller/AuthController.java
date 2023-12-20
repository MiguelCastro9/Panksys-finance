package com.api.controller;

import com.api.dto.request.SigninRequestDto;
import com.api.dto.request.RefreshTokenRequestDto;
import com.api.dto.request.UserRequestDto;
import com.api.dto.response.JWTResponseDto;
import com.api.dto.response.UserResponseDto;
import com.api.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.service.AuthService;
import com.api.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Miguel Castro
 */
@RestController
@RequestMapping("api/v1/auth")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Authentications")
public class AuthController {

    @Autowired
    private AuthService authenticationService;

    @Autowired
    UserService userService;

    @PostMapping("/singup")
    public ResponseEntity<UserResponseDto> singup(@Valid @RequestBody UserRequestDto userRequestDto) {
        if (!userRequestDto.getPassword().equals(userRequestDto.getPassword_repeated())) {
            return new ResponseEntity("Passwords is not equals.", HttpStatus.CONFLICT);
        }
        UserModel userModel = userService.singup(userRequestDto.convertUserDtoForEntity());
        UserResponseDto userResponseDto = UserResponseDto.convertEntityForUserDto(userModel);
        userResponseDto.add(linkTo(methodOn(AuthController.class).singup(userRequestDto)).withSelfRel());
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponseDto> signin(@RequestBody SigninRequestDto signinRequestDto) {
        JWTResponseDto jwtResponseDto = authenticationService.signin(signinRequestDto);
        jwtResponseDto.add(linkTo(methodOn(AuthController.class).signin(signinRequestDto)).withSelfRel());
        return new ResponseEntity<>(jwtResponseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        JWTResponseDto refreshTokenDto = authenticationService.refreshToken(refreshTokenRequestDto);
        refreshTokenDto.add(linkTo(methodOn(AuthController.class).refreshToken(refreshTokenRequestDto)).withSelfRel());
        return new ResponseEntity<>(refreshTokenDto, HttpStatus.OK);
    }
}
