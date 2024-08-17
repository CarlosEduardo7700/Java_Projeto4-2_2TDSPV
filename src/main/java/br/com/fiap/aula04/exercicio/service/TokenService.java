package br.com.fiap.aula04.exercicio.service;

import br.com.fiap.aula04.exercicio.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.token.secret}")
    private String senhaToken;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritimo = Algorithm.HMAC256(senhaToken);
            return JWT.create()
                    .withIssuer("API FIAP")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                    .sign(algoritimo);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token.");
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(senhaToken);
            return JWT.require(algorithm)
                    .withIssuer("API FIAP")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token não validado.");
        }
    }
}
