package br.gov.sp.fatec.springbootapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.springbootapp.security.JwtUtils;
import br.gov.sp.fatec.springbootapp.security.Login;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin
public class LoginController {

  @Autowired
  private AuthenticationManager authManager; //usado para fazer o login

  @PostMapping()
  public Login autenticar(@RequestBody Login login) throws JsonProcessingException { //metodo post autenticar, que recebe objeto tipo login
    Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()); //gera o objeto tipo authentication, passando user e senha
    auth = authManager.authenticate(auth); //faz autenticaçao, se errado gera exception
    login.setPassword(null); //apagar senha, para evitar risco
    login.setToken(JwtUtils.generateToken(auth)); //seta o token com o JwtUtils com base no auth gerado (linha 28)
    return login;
  }

  //Resumindo, pega user e senha, valida, gera token e volta
  
}
