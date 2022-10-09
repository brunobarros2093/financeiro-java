package com.moneyawapi.repository.lancamento;

import com.moneyawapi.model.Lancamento;
import com.moneyawapi.repository.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryQuery {

    List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);

}
