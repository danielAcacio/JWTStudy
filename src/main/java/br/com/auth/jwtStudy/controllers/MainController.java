package br.com.auth.jwtStudy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/public")
    public String publicUrl(){
        return "public url";
    }

    @GetMapping("/private")
    public String privateUrl(){
        return "private url";
    }
}
