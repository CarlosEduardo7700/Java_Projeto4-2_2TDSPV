package br.com.fiap.aula04.exercicio.dto.comentario;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroComentarioDto(@NotBlank @Size(max = 100) String conteudo, @Size(max = 50) String autor) {
}
