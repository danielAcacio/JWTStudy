package br.com.auth.jwtStudy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenDTO {
    String username;
    String token;
}
