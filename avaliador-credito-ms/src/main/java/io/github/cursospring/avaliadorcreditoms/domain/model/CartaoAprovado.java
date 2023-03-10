package io.github.cursospring.avaliadorcreditoms.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoAprovado {
    private String cartao;
    private String bandeira;
    private BigDecimal limiteAprovado;
}
