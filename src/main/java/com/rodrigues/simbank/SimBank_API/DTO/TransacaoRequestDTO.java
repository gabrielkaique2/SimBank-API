package com.rodrigues.simbank.SimBank_API.DTO;

import java.math.BigDecimal;

public abstract class TransacaoRequestDTO {

    private BigDecimal valor;

    public TransacaoRequestDTO() {
    }

    public TransacaoRequestDTO(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
