package br.com.auth.jwtStudy.auth.filters;

import br.com.auth.jwtStudy.auth.services.JWTService;
import br.com.auth.jwtStudy.auth.dto.UserDetailsImpl;
import br.com.auth.jwtStudy.auth.services.UserDitailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    JWTService jwtService;
    UserDitailsServiceImpl userDitailsService;

    public JWTFilter(JWTService jwtService, UserDitailsServiceImpl userDitailsService){
        this.jwtService = jwtService;
        this.userDitailsService = userDitailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header !=null && header.startsWith("Bearer")){
            String token = header.split(" ")[1];
            if(jwtService.isTokenValid(token)){
                String login = jwtService.getLogin(token);
                UserDetailsImpl user = userDitailsService.loadUserByUsername(login);
                UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
