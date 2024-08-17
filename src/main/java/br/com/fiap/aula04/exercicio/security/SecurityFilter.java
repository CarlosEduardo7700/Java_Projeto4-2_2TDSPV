package br.com.fiap.aula04.exercicio.security;

import br.com.fiap.aula04.exercicio.repository.UsuarioRepository;
import br.com.fiap.aula04.exercicio.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenJwt = request.getHeader("Authorization");
        if (tokenJwt != null) {
            tokenJwt = tokenJwt.replace("Bearer ", "");
            var subject = tokenService.getSubject(tokenJwt);
            var usuario = usuarioRepository.findByLogin(subject);
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
