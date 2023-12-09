package com.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Miguel Castro
 */
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    
    @GetMapping("/message")
    public String message() {
        return "admin ok";
    }
}