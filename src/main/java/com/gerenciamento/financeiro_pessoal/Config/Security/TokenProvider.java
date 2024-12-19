package com.gerenciamento.financeiro_pessoal.Config.Security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gerenciamento.financeiro_pessoal.Model.Usuario;
import com.gerenciamento.financeiro_pessoal.Repository.UsuarioRepository;
import com.auth0.jwt.JWT;

@Service
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration.minutes}")
    private long expiracaoTokenMinutos;

    // @Value("${jwt.refreshExpirationMinutes}")
    // private long refreshExpiracaoM;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String geradorToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secretKey);
            Map<String, Object> claims = new HashMap<>();
            claims.put("Email", usuario.getEmail());
            claims.put("ID", usuario.getUsuarioId());
            claims.put("Nome", usuario.getNome());

            return JWT.create()
                    .withIssuer("SistemaBancario")
                    .withExpiresAt(
                            LocalDateTime.now().plusMinutes(expiracaoTokenMinutos).toInstant(ZoneOffset.of("-03:00")))
                    .withClaim("user", claims)
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Falha ao gerar token JWT", exception);
        }
    }

    // public String geraRefreshTokenJWT(Usuario usuario) {
    // try {
    // Map<String, Object> claims = new HashMap<>();
    // claims.put("Email", usuario.getEmail());
    // claims.put("Nome", usuario.getNome());

    // String refresh = JWT.create()
    // .withIssuer("SistemaBancario")
    // .withExpiresAt(
    // LocalDateTime.now().plusMinutes(refreshExpiracaoM).toInstant(ZoneOffset.of("-03:00")))
    // .withClaim("user", claims)
    // .sign(Algorithm.HMAC256(secretKey));

    // usuarioRepository.save(usuario);

    // return refresh;
    // } catch (JWTCreationException e) {
    // throw new RuntimeException("Falha ao gerar refresh token", e);
    // }
    // }


    
    public String getUsuarioId(String tokenJWT) {
        Map<String, Object> claims = decodificadorTOKEN(tokenJWT);
        return String.valueOf(claims.get("ID"));
    }

    
    public String getSubject(String tokenJWT) {
        Map<String, Object> claims = decodificadorTOKEN(tokenJWT);
        return String.valueOf(claims.get("Email"));
    }

    
    public String getUsuarioNome(String tokenJWT) {
        Map<String, Object> claims = decodificadorTOKEN(tokenJWT);
        return String.valueOf(claims.get("Nome"));
    }

    //Função que decodifica o Token 
    public Map<String, Object> decodificadorTOKEN(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secretKey);
            DecodedJWT decodedJWT = JWT.require(algoritmo)
                    .withIssuer("SistemaBancario")
                    .build()
                    .verify(tokenJWT);

            Claim userClaim = decodedJWT.getClaim("user");
            return userClaim.asMap();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

}
