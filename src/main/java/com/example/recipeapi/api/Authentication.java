package com.example.recipeapi.api;

import com.example.recipeapi.models.LoginRequest;
import com.example.recipeapi.models.RegistrationRequest;
import com.example.recipeapi.models.AuthenticationResponse;
import com.example.recipeapi.repository.UserRepository;
import com.example.recipeapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController()
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class Authentication {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request){
        boolean emailPresent=this.userRepository.findByEmail(request.getEmail()).isPresent();
        boolean passwordSame=request.password.equals(request.confirmPassword);
        if(!emailPresent && passwordSame) {
            AuthenticationResponse response = this.authenticationService.register(request);
            return ResponseEntity.ok(response);
        }
       return ResponseEntity.badRequest().body(AuthenticationResponse.builder().build());
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody LoginRequest loginRequest){
        AuthenticationResponse response=this.authenticationService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
