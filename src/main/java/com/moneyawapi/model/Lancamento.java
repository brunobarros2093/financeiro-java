package com.moneyawapi.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String descricao;
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private String observacao;

    private TipoLancamento tipo;

    // uma categoria pode estar em varios lancamentos
    @ManyToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;

    // uma pessoa pode estar em varios lancamentos
    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    private Pessoa pessoa;

}
