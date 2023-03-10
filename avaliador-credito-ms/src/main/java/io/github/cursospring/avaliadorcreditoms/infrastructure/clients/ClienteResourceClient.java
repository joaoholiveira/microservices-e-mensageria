package io.github.cursospring.avaliadorcreditoms.infrastructure.clients;

import io.github.cursospring.avaliadorcreditoms.domain.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "clientes-ms", path = "/clientes")
public interface ClienteResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> buscarClientePorCpf(@RequestParam("cpf") String cpf);

}
