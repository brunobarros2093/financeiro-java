package com.moneyawapi.resource;

import com.moneyawapi.event.ResourceCriadoEvent;
import com.moneyawapi.model.Lancamento;
import com.moneyawapi.repository.LancamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentosRepository lancamentosRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Lancamento> getAll() {
        return lancamentosRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
        Optional<Lancamento> lancamento = lancamentosRepository.findById(codigo);
        return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Lancamento> cadastrarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lanc = lancamentosRepository.save(lancamento);
        publisher.publishEvent(new ResourceCriadoEvent(this, response, lanc.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lanc);

    }

}
