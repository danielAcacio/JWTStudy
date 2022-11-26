package br.com.auth.jwtStudy.auth.services;

import br.com.auth.jwtStudy.auth.dto.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JWTService {

    private String pass="123456";
    private Integer expiration=5;

    public String gerarToken (UserDetailsImpl user){
        LocalDateTime time = LocalDateTime.now().plusMinutes(expiration);
        Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, pass)
                .compact();
    }


    public Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.
                parser().
                setSigningKey(pass).
                parseClaimsJws(token).
                getBody();
    }

    public Boolean isTokenValid(String token){
        try{
            Claims claims = getClaims(token);
            LocalDateTime expiration = claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(expiration);
        }catch (Exception e){
            return false;
        }


    }

    public String getLogin(String token){
        return getClaims(token).getSubject();
    }
}
