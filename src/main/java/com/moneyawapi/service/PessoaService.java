package com.moneyawapi.service;

import com.moneyawapi.model.Pessoa;
import com.moneyawapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long codigo, Pessoa pessoa) {
        Pessoa pessoaEncontrada = getPessoaPorCodigo(codigo);
        BeanUtils.copyProperties(pessoa, pessoaEncontrada, "codigo");
        pessoaRepository.save(pessoaEncontrada);
        // TODO : set content-type for PATCH
        //  response.setHeader("Content-Type", "application/merge-patch+json");

        return pessoaEncontrada;
    }

    public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
        Pessoa pessoaEncontrada = getPessoaPorCodigo(codigo);
        pessoaEncontrada.setAtivo(ativo);
        pessoaRepository.save(pessoaEncontrada);
    }

    public Pessoa getPessoaPorCodigo(Long codigo) {
        return pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

}


