package com.lhama.lhamapersonalfinances.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("lhama-personal-finances")
                    .withSubject(user.getEmail())
                    .withClaim("idUser", user.getIdUser())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException e){
            throw new RuntimeException("Error While generating token", e);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return  JWT.require(algorithm)
                    .withIssuer("lhama-personal-finances")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            return "";
        }
    }
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    public Integer recoverIdFromToken(HttpHeaders header) {
        String token = header.get("Authorization").get(0);
        String jwt = token.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            Integer idUser = JWT.require(algorithm)
                    .withIssuer("lhama-personal-finances")
                    .build()
                    .verify(jwt)
                    .getClaim("idUser")
                    .asInt();
            return idUser;
        } catch (JWTVerificationException exception){
            return null;
        }
    }
}
