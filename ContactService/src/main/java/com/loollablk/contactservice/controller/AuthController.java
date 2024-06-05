package com.loollablk.contactservice.controller;

import com.loollablk.contactservice.entityDto.AuthRequestDTO;
import com.loollablk.contactservice.entityDto.JwtResponseDTO;
import com.loollablk.contactservice.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1/public")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping ("login")
    public JwtResponseDTO AuthenticateAndGetToken( @RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken (authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseDTO.builder()
                    .accessToken (jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .build ();
        } else {
            throw new UsernameNotFoundException ("invalid user request..!!");
        }
    }

    @PostMapping ("login")
    public JwtResponseDTO signUp( @RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken (authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseDTO.builder()
                    .accessToken (jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .build ();
        } else {
            throw new UsernameNotFoundException ("invalid user request..!!");
        }
    }
}
