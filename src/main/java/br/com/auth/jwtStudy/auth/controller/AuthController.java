package br.com.auth.jwtStudy.auth.controller;

import br.com.auth.jwtStudy.auth.dto.AuthDTO;
import br.com.auth.jwtStudy.auth.dto.TokenDTO;
import br.com.auth.jwtStudy.auth.services.JWTService;
import br.com.auth.jwtStudy.auth.dto.UserDetailsImpl;
import br.com.auth.jwtStudy.auth.services.UserDitailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    UserDitailsServiceImpl service;

    @Autowired
    JWTService jwtService;
    @PostMapping(value = "/auth", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public TokenDTO authenticate(String username, String password){
        UserDetailsImpl userData = service.isAuthenticated( new AuthDTO(username, password));
        if(userData != null){
            String token = jwtService.gerarToken(userData);
            return new TokenDTO(userData.getUsername(), token);
        }else{
            return null;
        }


    }
}
