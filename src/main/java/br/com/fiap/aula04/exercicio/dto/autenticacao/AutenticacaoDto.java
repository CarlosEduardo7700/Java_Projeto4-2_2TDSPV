package br.com.fiap.aula04.exercicio.dto.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDto(
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
