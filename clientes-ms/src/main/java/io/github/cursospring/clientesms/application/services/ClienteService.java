package io.github.cursospring.clientesms.application.services;

import io.github.cursospring.clientesms.domain.Cliente;
import io.github.cursospring.clientesms.infrastructure.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente){
        return repository.save(cliente);
    }

    public Optional<Cliente> getClienteByCPF(String cpf){
        return repository.findByCpf(cpf);
    }

}
