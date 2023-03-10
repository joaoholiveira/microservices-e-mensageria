package io.github.cursospring.cartoesms.application.services;

import io.github.cursospring.cartoesms.domain.ClienteCartao;
import io.github.cursospring.cartoesms.infrastructure.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> findByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}
