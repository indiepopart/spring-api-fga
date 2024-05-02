package com.example.api.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @GetMapping("/greeting")
    public String greeting(Principal principal) {
        return "Hello, " + principal.getName() + "!";
    }

}