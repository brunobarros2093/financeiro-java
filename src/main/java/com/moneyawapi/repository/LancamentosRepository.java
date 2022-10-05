package com.moneyawapi.repository;

import com.moneyawapi.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentosRepository extends JpaRepository<Lancamento, Long> {
}
