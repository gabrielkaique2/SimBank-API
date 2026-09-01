package com.rodrigues.simbank.SimBank_API.DTO;

import java.math.BigDecimal;

public class TransferenciaRequestDTO extends TransacaoRequestDTO{

    private Long contaOrigemId;
    private Long contaDestinoId;

    public TransferenciaRequestDTO() {
    }

    public TransferenciaRequestDTO(BigDecimal valor, Long contaOrigemId, Long contaDestinoId) {
        super(valor);
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
    }

    public Long getContaOrigemId() { return contaOrigemId; }

    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }
}
