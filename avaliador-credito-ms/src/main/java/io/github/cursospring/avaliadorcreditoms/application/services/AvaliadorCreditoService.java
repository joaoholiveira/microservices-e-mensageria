package io.github.cursospring.avaliadorcreditoms.application.services;

import feign.FeignException;
import io.github.cursospring.avaliadorcreditoms.application.services.exceptions.DadosClienteNotFoundException;
import io.github.cursospring.avaliadorcreditoms.application.services.exceptions.ErroComunicacaoMicroservicesException;
import io.github.cursospring.avaliadorcreditoms.domain.model.CartaoCliente;
import io.github.cursospring.avaliadorcreditoms.domain.model.DadosCliente;
import io.github.cursospring.avaliadorcreditoms.domain.model.SituacaoCliente;
import io.github.cursospring.avaliadorcreditoms.infrastructure.clients.CartoesResourceClient;
import io.github.cursospring.avaliadorcreditoms.infrastructure.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException,
            ErroComunicacaoMicroservicesException {
        try{
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.buscarClientePorCpf(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesCliente  = cartoesClient.getCartoesByCpf(cpf);

            return SituacaoCliente.builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesCliente.getBody())
                    .build();
        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status)
                throw new DadosClienteNotFoundException();
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

}
