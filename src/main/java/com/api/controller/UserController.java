package com.api.controller;

import com.api.dto.request.UserRequestDto;
import com.api.model.UserModel;
import com.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Miguel Castro
 */
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/message")
    public String message() {
        return "user ok";
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserRequestDto userRequestDto) {
        if (!userRequestDto.getPassword().equals(userRequestDto.getPasswordRepeated())) {
            return new ResponseEntity<>("Passwords is not equals.", HttpStatus.CONFLICT);
        }
        UserModel userModel = userService.update(id, userRequestDto.convertUserUpdateDtoForEntity());
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }

}
