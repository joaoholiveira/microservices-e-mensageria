package io.github.cursospring.clientesms.application;

import io.github.cursospring.clientesms.application.representation.ClienteRequest;
import io.github.cursospring.clientesms.application.services.ClienteService;
import io.github.cursospring.clientesms.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteResource {

    private final ClienteService service;

    @GetMapping
    public String status(){
        return "UP";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteRequest request){
        Cliente cliente = request.toDomain();
        service.save(cliente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping(params = "cpf")
    public ResponseEntity buscarClientePorCpf(@RequestParam("cpf") String cpf){
        Optional<Cliente> cliente = service.getClienteByCPF(cpf);
        if (cliente.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente);
    }

}
