package io.github.cursospring.avaliadorcreditoms.application.services;

import io.github.cursospring.avaliadorcreditoms.domain.model.DadosCliente;
import io.github.cursospring.avaliadorcreditoms.domain.model.SituacaoCliente;
import io.github.cursospring.avaliadorcreditoms.infrastructure.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.buscarClientePorCpf(cpf);

        return SituacaoCliente.builder()
                .cliente(dadosClienteResponse.getBody())
                .build();
    }

}
