package br.com.auth.jwtStudy.auth.services;

import br.com.auth.jwtStudy.auth.dto.AuthDTO;
import br.com.auth.jwtStudy.auth.dto.UserDetailsImpl;
import br.com.auth.jwtStudy.models.User;
import br.com.auth.jwtStudy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDitailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public UserDetailsImpl isAuthenticated(AuthDTO auth){
        UserDetailsImpl u = this.loadUserByUsername(auth.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches( auth.getPassword(), u.getPassword()))
            return u;
        else
            return null;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
       User u  = userRepository.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("erro"));
       return new UserDetailsImpl(u);
    }
}
