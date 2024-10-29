package com.gestor.eventos.Security;

import com.gestor.eventos.exceptions.GestorAppException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final Key jwtSecretKey;

    // Constructor que utiliza una clave secreta fija desde application.properties
    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes()); // Fija la clave secreta
    }

    public String generarToken(Authentication authentication) {
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + 604800000); // 1 semana

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(fechaExpiracion)
                .signWith(jwtSecretKey) // Firma con la clave secreta
                .compact();
    }

    public String obtenerUsernameDelJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey) // Validar con la clave secreta
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validarToken(String token) {
        try {
            System.out.println("Validando token: " + token); // Log para ver el token
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Firma JWT no válida: " + ex.getMessage());
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "Firma JWT no válida");
        } catch (MalformedJwtException ex) {
            System.out.println("Token JWT no válido: " + ex.getMessage());
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "Token JWT no válido");
        } catch (ExpiredJwtException ex) {
            System.out.println("Token JWT caducado: " + ex.getMessage());
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "Token JWT caducado");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Token JWT no compatible: " + ex.getMessage());
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "Token JWT no compatible");
        } catch (IllegalArgumentException ex) {
            System.out.println("La cadena claims JWT está vacía: " + ex.getMessage());
            throw new GestorAppException(HttpStatus.BAD_REQUEST, "La cadena claims JWT está vacía");
        }
    }
}
