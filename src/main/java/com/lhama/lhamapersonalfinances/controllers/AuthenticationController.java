package com.lhama.lhamapersonalfinances.controllers;

import com.lhama.lhamapersonalfinances.infra.security.LoginResponseDTO;
import com.lhama.lhamapersonalfinances.infra.security.TokenService;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.user.UserAuthenticationDTO;
import com.lhama.lhamapersonalfinances.model.entities.user.UserDTO;
import com.lhama.lhamapersonalfinances.model.entities.user.UserRegisterDTO;
import com.lhama.lhamapersonalfinances.model.repositorys.UserRepository;
import com.lhama.lhamapersonalfinances.model.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid UserAuthenticationDTO userAuthenticationData){
        var userNamePassword = new UsernamePasswordAuthenticationToken(userAuthenticationData.email(), userAuthenticationData.password());
        var auth = authManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid UserRegisterDTO userRegisterData, UriComponentsBuilder uriBuilder){
        User newUser = userService.registerUser(userRegisterData);

        if (newUser == null) return ResponseEntity.internalServerError().build();

        Long idNewUser = newUser.getIdUser();

        URI uri = uriBuilder.path("auth/register/{id}").buildAndExpand(idNewUser).toUri();

        return ResponseEntity.created(uri).body(new UserDTO(newUser));
    }
}
