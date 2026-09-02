package com.rodrigues.simbank.SimBank_API.DTO;

import java.math.BigDecimal;

public class SaqueRequestDTO extends TransacaoRequestDTO{

    private Long contaOrigemId;

    public SaqueRequestDTO() {
    }

    public SaqueRequestDTO(BigDecimal valor, Long contaOriemId) {
        super(valor);
        this.contaOrigemId = contaOriemId;
    }

    public Long getContaOrigemId() {
        return contaOrigemId;
    }

    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }
}
