package io.github.cursospring.cartoesms.infrastructure.mqueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cursospring.cartoesms.domain.Cartao;
import io.github.cursospring.cartoesms.domain.ClienteCartao;
import io.github.cursospring.cartoesms.domain.DadosSolicitacaoEmissaoCartao;
import io.github.cursospring.cartoesms.infrastructure.repository.CartaoRepository;
import io.github.cursospring.cartoesms.infrastructure.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {
    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSoliciatacaoEmissaoCartao(@Payload String payload){
        try{
            ObjectMapper mapper = new ObjectMapper();

            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
