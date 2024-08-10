package br.com.fiap.aula04.exercicio.controller;

import br.com.fiap.aula04.exercicio.dto.usuario.CadastroUsuarioDto;
import br.com.fiap.aula04.exercicio.dto.usuario.DadosTokenJwtDto;
import br.com.fiap.aula04.exercicio.dto.usuario.DetalhesUsuarioDto;
import br.com.fiap.aula04.exercicio.model.Usuario;
import br.com.fiap.aula04.exercicio.repository.UsuarioRepository;
import br.com.fiap.aula04.exercicio.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("register")
    @Transactional
    public ResponseEntity<DetalhesUsuarioDto> post(@RequestBody @Valid CadastroUsuarioDto dto, UriComponentsBuilder builder) {
        var usuario = new Usuario(dto.login(), passwordEncoder.encode(dto.senha()));
        usuarioRepository.save(usuario);
        var uri = builder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesUsuarioDto(usuario));
    }

    @PostMapping("login")
    public ResponseEntity<DadosTokenJwtDto> login(@RequestBody DetalhesUsuarioDto dto) {
        var token = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        var authentication = manager.authenticate(token);
        var tokenJwt = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJwtDto(tokenJwt));
    }
}
