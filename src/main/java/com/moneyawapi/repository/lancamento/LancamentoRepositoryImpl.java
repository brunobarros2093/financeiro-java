package com.moneyawapi.repository.lancamento;

import com.moneyawapi.model.Lancamento;
import com.moneyawapi.repository.filter.LancamentoFilter;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        // criar restrições
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicados = new ArrayList<>();

        if (ObjectUtils.isEmpty(lancamentoFilter.getDescricao())) {
            predicados.add(builder.like(
                    builder.lower(root.get("descricao")),
                    "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"
            ));
        }

        if (ObjectUtils.isEmpty(lancamentoFilter.getDataVencimentoDe())) {
            predicados.add(builder.greaterThanOrEqualTo(
                    root.get("dataVencimento"),
                    "%" + lancamentoFilter.getDataVencimentoDe() + "%"
            ));
        }

        if (ObjectUtils.isEmpty(lancamentoFilter.getDataVencimentoAte())) {
            predicados.add(builder.lessThanOrEqualTo(
                    root.get("dataVencimento"),
                    "%" + lancamentoFilter.getDataVencimentoAte() + "%"
            ));
        }

        return predicados.toArray(new Predicate[predicados.size()]);
    }
}
