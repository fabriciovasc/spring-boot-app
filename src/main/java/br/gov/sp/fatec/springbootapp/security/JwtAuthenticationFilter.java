package br.gov.sp.fatec.springbootapp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
        // Método responsável por receber a requisição, resposta e o chain (controle de cadeias que chama sempre o próximo filtro até chegar no Controller)

    try {
      // Cria objeto com a requisição
      HttpServletRequest servletRequest = (HttpServletRequest) request;
      // Filtra a requisição pelo parâmetro Authorization contido no Header
      String authorization = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
      if (authorization != null) {
        // Cria as credencias através da validação do token (tempo de expiração, etc) e faz o replace para mandar somente o token
        Authentication credentials = JwtUtils.parseToken(authorization.replaceAll("Bearer ", ""));
        // Com o token validado e sem exceções, realizamos uma autenticação com as credenciais montadas
        SecurityContextHolder.getContext().setAuthentication(credentials);
      }
      chain.doFilter(request, response);
      // Chama o próximo filtro
    } catch (Throwable t) {
      HttpServletResponse servletResponse = (HttpServletResponse) response;
      servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, t.getMessage());
    }
  }

}
