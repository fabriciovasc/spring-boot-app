package br.gov.sp.fatec.springbootapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// Habilita a segurança padrão e a segurança por anotação
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;
  // Declarando o serviço para o LOGIN

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable() // Desabilita o CSRF (forma de token antigo)
        .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).sessionManagement() // Adiciona filtros antes de entrar no Spring Security
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // O login só acontece quando há requisição, ou seja, cada requisição vai ter tratada com um novo login e o antigo será jogado fora
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    // Serviço que busca os dados do usuário para autenticação
    auth.userDetailsService(userDetailsService);
  }

  @Bean
  public PasswordEncoder passwordEncoderBean() {
    // Criptografia BCrypt para a senha da autenticação
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  // Disponibiliza o uso em injeção para a instância do AuthenticationManager, que realiza o processo de auth
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
