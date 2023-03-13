package io.github.cursospring.avaliadorcreditoms.application.services;

import feign.FeignException;
import io.github.cursospring.avaliadorcreditoms.application.services.exceptions.DadosClienteNotFoundException;
import io.github.cursospring.avaliadorcreditoms.application.services.exceptions.ErroComunicacaoMicroservicesException;
import io.github.cursospring.avaliadorcreditoms.application.services.exceptions.ErroSolicitacaoCartaoException;
import io.github.cursospring.avaliadorcreditoms.domain.model.*;
import io.github.cursospring.avaliadorcreditoms.infrastructure.clients.CartoesResourceClient;
import io.github.cursospring.avaliadorcreditoms.infrastructure.clients.ClienteResourceClient;
import io.github.cursospring.avaliadorcreditoms.infrastructure.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    private final SolicitacaoEmissaoCartaoPublisher publisher;

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

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws  DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.buscarClientePorCpf(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            List<CartaoAprovado> cartoesAprovados = cartoes.stream().map(cartao -> {
                BigDecimal limiteAprovado = calcularLimiteCartao(dadosClienteResponse.getBody().getIdade(), cartao.getLimiteBasico());

                CartaoAprovado cartaoAprovado = new CartaoAprovado();
                cartaoAprovado.setCartao(cartao.getNome());
                cartaoAprovado.setBandeira(cartao.getBandeira());
                cartaoAprovado.setLimiteAprovado(limiteAprovado);

                return cartaoAprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(cartoesAprovados);

        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status)
                throw new DadosClienteNotFoundException();
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try{
            publisher.solicitarCartao(dados);
            String protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch(Exception e){
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }

    private BigDecimal calcularLimiteCartao(Integer idade, BigDecimal limiteBasico){
        BigDecimal idadeAsBigDecimal = BigDecimal.valueOf(idade);
        idadeAsBigDecimal = idadeAsBigDecimal.divide(BigDecimal.valueOf(10));
        return idadeAsBigDecimal.multiply(limiteBasico);
    }

}
