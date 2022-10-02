package com.moneyawapi.resource;

import com.moneyawapi.event.ResourceCriadoEvent;
import com.moneyawapi.model.Categoria;
import com.moneyawapi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        publisher.publishEvent(new ResourceCriadoEvent(this, response, categoriaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);

    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPeloCodigo(@PathVariable long codigo) {
        Optional<Categoria> categoria = categoriaRepository.findById(codigo);
        return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
    }

}
