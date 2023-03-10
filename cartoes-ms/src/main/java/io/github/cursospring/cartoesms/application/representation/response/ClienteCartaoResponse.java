package io.github.cursospring.cartoesms.application.representation.response;

import io.github.cursospring.cartoesms.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCartaoResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static ClienteCartaoResponse fromModel(ClienteCartao model){
        return new ClienteCartaoResponse(
                model.getCartao().getNome(),
                model.getCartao().getBandeiraCartao().toString(),
                model.getLimite()
        );
    }
}
