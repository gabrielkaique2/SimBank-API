package com.rodrigues.simbank.SimBank_API.DTO;

import java.math.BigDecimal;

public class DepositoRequestDTO extends TransacaoRequestDTO{

    private Long contaDestinoId;

    public DepositoRequestDTO() {
    }

    public DepositoRequestDTO(BigDecimal valor, Long contaDestinoId) {
        super(valor);
        this.contaDestinoId = contaDestinoId;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }
}
