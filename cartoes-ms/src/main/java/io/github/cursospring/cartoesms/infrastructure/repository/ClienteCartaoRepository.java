package io.github.cursospring.cartoesms.infrastructure.repository;

import io.github.cursospring.cartoesms.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {

    List<ClienteCartao> findByCpf(String Cpf);

}
