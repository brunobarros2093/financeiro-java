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
        Pessoa pessoaEncontrada = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(pessoa, pessoaEncontrada, "codigo");
        pessoaRepository.save(pessoaEncontrada);
        // TODO : set content-type for PATCH
        //  response.setHeader("Content-Type", "application/merge-patch+json");

        return pessoaEncontrada;
    }

}
