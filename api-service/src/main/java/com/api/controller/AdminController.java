package com.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Miguel Castro
 */
@RestController
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Administrators")
@RequestMapping("api/v1/admin")
public class AdminController {
    
    @GetMapping("/message")
    public String message() {
        return "admin ok";
    }
}
