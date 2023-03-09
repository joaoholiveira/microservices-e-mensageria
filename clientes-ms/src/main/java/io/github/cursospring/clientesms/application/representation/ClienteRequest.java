package io.github.cursospring.clientesms.application.representation;

import io.github.cursospring.clientesms.domain.Cliente;
import lombok.Data;

@Data
public class ClienteRequest {

    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toDomain(){
        return new Cliente(cpf, nome, idade);
    }
}
