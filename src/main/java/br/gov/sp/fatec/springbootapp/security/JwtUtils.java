package br.gov.sp.fatec.springbootapp.security;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

  private static final String KEY = "spring.jwt.sec";

  public static String generateToken(Authentication usuario) throws JsonProcessingException {
    // Cria um objeto para ser usado na montagem do token JWT com as credencias (username e autorização)
    ObjectMapper mapper = new ObjectMapper();
    Login usuarioSemSenha = new Login();
    usuarioSemSenha.setUsername(usuario.getName());
    if (!usuario.getAuthorities().isEmpty()) {
      usuarioSemSenha.setAuth(usuario.getAuthorities().iterator().next().getAuthority());
    }
    String usuarioJson = mapper.writeValueAsString(usuarioSemSenha);
    // Cria a validade desse token
    Date agora = new Date();
    Long hora = 1000L * 60L * 60L;
  
    // Compacta todas as informações geradas através do objeto de autenticação recebido no método
    return Jwts.builder().claim("userDetails", usuarioJson).setIssuer("br.gov.sp.fatec").setSubject(usuario.getName()) 
        .setExpiration(new Date(agora.getTime() + hora)).signWith(SignatureAlgorithm.HS512, KEY).compact();
  }

  public static Authentication parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
    // Transforma o token recebido em um objeto de autenticação para que seja possível o filtro validar a expiração e os dados (username e autorização)
    ObjectMapper mapper = new ObjectMapper();
    String credentialsJson = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().get("userDetails", String.class);
    Login usuario = mapper.readValue(credentialsJson, Login.class);
    UserDetails userDetails = User.builder().username(usuario.getUsername()).password("secret")
        .authorities(usuario.getAuth()).build();
    return new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword(),
        userDetails.getAuthorities());
  }

}