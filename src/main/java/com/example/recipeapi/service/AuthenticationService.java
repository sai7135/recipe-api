package com.example.recipeapi.service;

import com.example.recipeapi.models.LoginRequest;
import com.example.recipeapi.models.RegistrationRequest;
import com.example.recipeapi.models.AuthenticationResponse;
import com.example.recipeapi.models.entity.User;
import com.example.recipeapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegistrationRequest request){
        User user=User.builder().email(request.email).password(passwordEncoder.encode(request.password)).build();
        this.userRepository.save(user);
        var jwtToken=this.jwtService.buildToken(user);
        AuthenticationResponse authenticationResponse= AuthenticationResponse
                .builder()
                .jwtToken(jwtToken)
                .expiration(this.jwtService.getExpiration(jwtToken).toString())
                .build();
        return authenticationResponse;
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        this.authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()
                        )
                );
        User user=this.userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UsernameNotFoundException(""));
        var jwtToken=this.jwtService.buildToken(user);
        AuthenticationResponse authenticationResponse= AuthenticationResponse
                .builder()
                .jwtToken(jwtToken)
                .expiration(this.jwtService.getExpiration(jwtToken).toString())
                .build();
        return authenticationResponse;
    }

}
