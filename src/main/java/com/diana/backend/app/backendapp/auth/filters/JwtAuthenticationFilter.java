package com.diana.backend.app.backendapp.auth.filters;

import static com.diana.backend.app.backendapp.auth.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.diana.backend.app.backendapp.auth.TokenJwtConfig.PREFIX_TOKEN;
import static com.diana.backend.app.backendapp.auth.TokenJwtConfig.SECRET_KEY;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.diana.backend.app.backendapp.models.entities.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

  private final UserDetailsService userDetailsService;

private AuthenticationManager authenticationManager;
  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService){
   this.authenticationManager = authenticationManager;
   this.userDetailsService = userDetailsService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
        User user = null;
        String username = null;
        String password = null;

        try {
          user = new ObjectMapper().readValue(request.getInputStream(), User.class);
          username = user.getUsername();
          password = user.getPassword();

          UserDetails userDetails =  userDetailsService.loadUserByUsername(username);
          boolean passwordMatch = BCrypt.checkpw(password, userDetails.getPassword());
          if (passwordMatch) {
              System.out.println("Autenticación exitosa");
          } else {
              System.out.println("Credenciales inválidas");
          }

          logger.info("Authentication");
        } catch (StreamReadException e) {
          e.printStackTrace();
        } catch (DatabindException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();

        String originalText = SECRET_KEY + ":" + username;
        String token = Base64.getEncoder().encodeToString(originalText.getBytes());

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", String.format("%s, Sesion iniciada con exito", username));
        body.put("username", username);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Correo y/o usuario incorrectos");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
  }
  
}
