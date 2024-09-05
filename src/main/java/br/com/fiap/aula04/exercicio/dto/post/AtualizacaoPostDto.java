package br.com.fiap.aula04.exercicio.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
@Schema(description = "Informações para atualização de um Post")
public record AtualizacaoPostDto(
        @Schema(description = "Título do Post")
        @Size(max = 50)
        String titulo,
        @Schema(description = "Conteúdo do Post")
        @Size(max = 500)
        String conteudo,
        @Schema(description = "Autor do Post")
        @Size(max = 50)
        String autor) {
}
