package br.com.fiap.aula04.exercicio.controller;

import br.com.fiap.aula04.exercicio.dto.comentario.CadastroComentarioDto;
import br.com.fiap.aula04.exercicio.dto.comentario.DetalhesComentarioDto;
import br.com.fiap.aula04.exercicio.dto.post.AtualizacaoPostDto;
import br.com.fiap.aula04.exercicio.dto.post.CadastroPostDto;
import br.com.fiap.aula04.exercicio.dto.post.DetalhesPostDto;
import br.com.fiap.aula04.exercicio.model.Comentario;
import br.com.fiap.aula04.exercicio.model.Post;
import br.com.fiap.aula04.exercicio.repository.ComentarioRepository;
import br.com.fiap.aula04.exercicio.repository.PostRepository;
import br.com.fiap.aula04.exercicio.repository.TagRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private TagRepository tagRepository;

    @PutMapping("{idPost}/tags/{idTag}")
    @Transactional
    public ResponseEntity<DetalhesPostDto> put(@PathVariable("idPost") Long idPost, @PathVariable("idTag") Long idTag){
        var post = postRepository.getReferenceById(idPost);
        var tag = tagRepository.getReferenceById(idTag);
        tag.getPosts().add(post);
        return ResponseEntity.ok().body(new DetalhesPostDto(post));
    }

    @PostMapping("{id}/comentarios")
    @Transactional
    public ResponseEntity<DetalhesComentarioDto> post(@PathVariable("id") Long id,
                                                      @RequestBody @Valid CadastroComentarioDto dto,
                                                      UriComponentsBuilder uriBuilder){
        var post = postRepository.getReferenceById(id);
        var comentario = new Comentario(dto, post);
        comentarioRepository.save(comentario);
        var uri = uriBuilder.path("comentarios/{id}").buildAndExpand(comentario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesComentarioDto(comentario));
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesPostDto> find(@PathVariable("id") Long id){
        var post = postRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhesPostDto(post));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalhesPostDto> put(@PathVariable("id") Long id,
                                               @RequestBody @Valid AtualizacaoPostDto dto){
        var post = postRepository.getReferenceById(id);
        post.atualizar(dto);
        return ResponseEntity.ok(new DetalhesPostDto(post));
    }

    @GetMapping
    public ResponseEntity<List<DetalhesPostDto>> list(Pageable pageable){
        var lista = postRepository.findAll(pageable).stream().map(DetalhesPostDto::new).toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhesPostDto> create(@RequestBody @Valid CadastroPostDto dto,
                                                  UriComponentsBuilder builder){
        var post = new Post(dto);
        postRepository.save(post);
        var url = builder.path("posts/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesPostDto(post));
    }

}
