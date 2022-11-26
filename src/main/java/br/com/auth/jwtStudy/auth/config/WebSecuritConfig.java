package br.com.auth.jwtStudy.auth.config;


import br.com.auth.jwtStudy.auth.filters.JWTFilter;
import br.com.auth.jwtStudy.auth.services.JWTService;
import br.com.auth.jwtStudy.auth.services.UserDitailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecuritConfig {

    @Autowired
    UserDitailsServiceImpl uerDetailsService;
    @Autowired
    JWTService jwtService;


    @Bean
    public JWTFilter jwtFilter(){
        return new JWTFilter(jwtService, uerDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager configAuthRules(HttpSecurity http, BCryptPasswordEncoder encoder, UserDitailsServiceImpl userDetaislServices) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetaislServices)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.
        csrf().
        disable().
        authorizeRequests().
        antMatchers("/public").
        permitAll().antMatchers("/auth").
        permitAll().
        anyRequest().
        authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
