package br.com.fiap.aula04.exercicio.dto.usuario;

public record DadosTokenJwtDto(String token) {
    public DadosTokenJwtDto(String token) {
        this.token = token;
    }
}
