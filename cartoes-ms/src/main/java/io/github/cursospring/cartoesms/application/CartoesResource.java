package io.github.cursospring.cartoesms.application;

import io.github.cursospring.cartoesms.application.representation.request.CartaoRequest;
import io.github.cursospring.cartoesms.application.representation.response.ClienteCartaoResponse;
import io.github.cursospring.cartoesms.application.services.CartaoService;
import io.github.cursospring.cartoesms.application.services.ClienteCartaoService;
import io.github.cursospring.cartoesms.domain.Cartao;
import io.github.cursospring.cartoesms.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "UP";
    }

    @PostMapping
    public ResponseEntity cadastrarCartao(@RequestBody CartaoRequest request){
        Cartao cartao = request.toDomain();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> cartoesRendaMaiorOuIgual = cartaoService.getCartoesRendaMaiorOuIgual(renda);
        return ResponseEntity.ok(cartoesRendaMaiorOuIgual);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<ClienteCartaoResponse>> getCartoesByCpf(@RequestParam("cpf") String cpf){
        List<ClienteCartao> clienteCartao = clienteCartaoService.findByCpf(cpf);
        List<ClienteCartaoResponse> response = clienteCartao.stream()
                .map(ClienteCartaoResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

}
