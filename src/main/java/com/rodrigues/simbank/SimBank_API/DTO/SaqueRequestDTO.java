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

    public Long getContaOriemId() {
        return contaOrigemId;
    }

    public void setContaOriemId(Long contaOriemId) {
        this.contaOrigemId = contaOriemId;
    }
}
