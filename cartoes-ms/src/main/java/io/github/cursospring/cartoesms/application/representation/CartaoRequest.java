package io.github.cursospring.cartoesms.application.representation;

import io.github.cursospring.cartoesms.domain.Cartao;
import io.github.cursospring.cartoesms.domain.enums.BandeiraCartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toDomain(){
        return new Cartao(nome, bandeira, renda, limite);
    }

}
