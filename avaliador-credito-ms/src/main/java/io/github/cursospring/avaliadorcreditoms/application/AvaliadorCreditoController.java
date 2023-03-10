package io.github.cursospring.avaliadorcreditoms.application;

import io.github.cursospring.avaliadorcreditoms.application.services.AvaliadorCreditoService;
import io.github.cursospring.avaliadorcreditoms.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status(){
        return "UP";
    }

    @GetMapping("/situacao-cliente")
    public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf){
        SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);

        return ResponseEntity.ok(situacaoCliente);
    }

}
