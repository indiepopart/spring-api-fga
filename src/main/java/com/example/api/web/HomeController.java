package com.example.api.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @GetMapping("/greeting")
    @PreAuthorize("@fga.check('resource', 'greeting', 'viewer', 'user', authentication?.name)")
    public String greeting(Principal principal) {
        return "Hello, " + principal.getName() + "!";
    }

    @GetMapping("/hello")
    @PreAuthorize("@fga.check('resource', 'greeting', 'viewer', 'user')")
    public String hello(Principal principal) {
        return "Hello, " + principal.getName() + "!";
    }

    @GetMapping("/hola")
    public String hola(Authentication authentication) {
        return "Hello, " + authentication.getName() + "!";
    }
}