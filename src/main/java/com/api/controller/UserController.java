package com.api.controller;

import com.api.dto.request.UserRequestDto;
import com.api.model.UserModel;
import com.api.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Miguel Castro
 */
@RestController
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Users")
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
        UserModel builder = userService.update(id, userRequestDto.convertUserUpdateDtoForEntity());
        builder.add(linkTo(methodOn(UserController.class).update(id, userRequestDto)).withSelfRel());
        return new ResponseEntity<>(builder, HttpStatus.OK);
    }

    @PutMapping("/disabled/{id}")
    public ResponseEntity<?> disabled(@PathVariable Long id) {
        UserModel builder = userService.disabled(id);
        builder.add(linkTo(methodOn(UserController.class).disabled(id)).withSelfRel());
        return new ResponseEntity<>(builder, HttpStatus.OK);
    }
}
