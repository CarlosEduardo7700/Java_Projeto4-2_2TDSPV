package br.com.fiap.aula04.exercicio.dto.autenticacao;

public record DadosTokenJwtDto(
        String token
) {
    public DadosTokenJwtDto(String token) {
        this.token = token;
    }
}
