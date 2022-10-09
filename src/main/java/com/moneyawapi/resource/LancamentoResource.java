package com.moneyawapi.resource;

import com.moneyawapi.event.ResourceCriadoEvent;
import com.moneyawapi.exceptionhandler.GeneralExceptionHandler;
import com.moneyawapi.model.Lancamento;
import com.moneyawapi.repository.LancamentosRepository;
import com.moneyawapi.repository.filter.LancamentoFilter;
import com.moneyawapi.service.LancamentoService;
import com.moneyawapi.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private MessageSource messageSource;
    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
        return lancamentosRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
        Optional<Lancamento> lancamento = lancamentosRepository.findById(codigo);
        return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Lancamento> cadastrarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lanc = lancamentoService.salvar(lancamento);
        publisher.publishEvent(new ResourceCriadoEvent(this, response, lanc.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lanc);
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
        String mensagem = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String msgDev = ex.toString();
        List<GeneralExceptionHandler.Erro> erros = List.of(new GeneralExceptionHandler.Erro(mensagem, msgDev));
        return ResponseEntity.badRequest().body(erros);
    }

}
