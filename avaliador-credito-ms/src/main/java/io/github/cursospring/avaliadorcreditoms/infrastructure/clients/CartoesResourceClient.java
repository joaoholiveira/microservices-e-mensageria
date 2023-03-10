package io.github.cursospring.avaliadorcreditoms.infrastructure.clients;

import io.github.cursospring.avaliadorcreditoms.domain.model.CartaoCliente;
import io.github.cursospring.avaliadorcreditoms.domain.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "cartoes-ms", path = "/cartoes")
public interface CartoesResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCpf(@RequestParam("cpf") String cpf);

}
