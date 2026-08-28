package com.rodrigues.simbank.SimBank_API.DTO;

import com.rodrigues.simbank.SimBank_API.Utilidades.TipoConta;

import java.math.BigDecimal;

public class ContaRequestDTO {

    private Long usuarioId;
    private TipoConta tipoconta;

    public ContaRequestDTO() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public TipoConta getTipoconta() { return tipoconta;}
    public void setTipoconta(TipoConta tipoconta) {
        this.tipoconta = tipoconta;
    }
}
