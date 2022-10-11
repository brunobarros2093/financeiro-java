package com.moneyawapi.repository.lancamento;

import com.moneyawapi.model.Lancamento;
import com.moneyawapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {
    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

}
