package br.com.fiap.aula04.exercicio.model;

import br.com.fiap.aula04.exercicio.dto.post.AtualizacaoPostDto;
import br.com.fiap.aula04.exercicio.dto.post.CadastroPostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name="TB_POST")
public class Post {

    @Id
    @GeneratedValue
    @Column(name="cd_post")
    private Long id;

    @ManyToMany(mappedBy = "posts")
    private Set<Tag> tags = new HashSet<>();

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private DetalhesPost detalhes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comentario> comentarios;

    @Column(name="ds_titulo", nullable = false, length = 50)
    private String titulo;

    @Column(name="ds_conteudo", nullable = false, length = 500)
    private String conteudo;

    public Post(CadastroPostDto dto) {
        titulo = dto.titulo();
        conteudo = dto.conteudo();
        detalhes = new DetalhesPost(dto);
        detalhes.setPost(this); //Setando a FK do banco
    }

    public void atualizar(AtualizacaoPostDto dto) {
        if (dto.titulo() != null)
            this.titulo = dto.titulo();
        if (dto.conteudo() != null)
            this.conteudo = dto.conteudo();
        if (dto.autor() != null)
            this.detalhes.setAutor(dto.autor());
    }
}
