package io.github.cursospring.cartoesms.application;

import io.github.cursospring.cartoesms.application.representation.CartaoRequest;
import io.github.cursospring.cartoesms.application.services.CartaoService;
import io.github.cursospring.cartoesms.domain.Cartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService service;

    @GetMapping
    public String status(){
        return "UP";
    }

    @PostMapping
    public ResponseEntity cadastrarCartao(@RequestBody CartaoRequest request){
        Cartao cartao = request.toDomain();
        service.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> cartoesRendaMaiorOuIgual = service.getCartoesRendaMaiorOuIgual(renda);
        return ResponseEntity.ok(cartoesRendaMaiorOuIgual);
    }

}
