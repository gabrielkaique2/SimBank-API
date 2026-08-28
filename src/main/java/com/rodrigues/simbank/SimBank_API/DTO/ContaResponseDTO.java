package com.rodrigues.simbank.SimBank_API.DTO;

import com.rodrigues.simbank.SimBank_API.Utilidades.TipoConta;

import java.math.BigDecimal;

public class ContaResponseDTO {

    private Long rowid;
    private String numeroConta;
    private BigDecimal saldoAtual;
    private Long usuarioId;
    private TipoConta tipoconta;

    public ContaResponseDTO() {
    }

    public ContaResponseDTO(Long rowid, String numeroConta, BigDecimal saldoAtual, Long usuarioId, TipoConta tipoconta) {
        this.rowid = rowid;
        this.numeroConta = numeroConta;
        this.saldoAtual = saldoAtual;
        this.usuarioId = usuarioId;
        this.tipoconta = tipoconta;
    }

    public Long getRowid() {return rowid;}
    public void setRowid(Long rowid) {this.rowid = rowid;}

    public String getNumeroConta() {return numeroConta;}
    public void setNumeroConta(String numeroConta) {this.numeroConta = numeroConta;}

    public BigDecimal getSaldoAtual() {return saldoAtual;}
    public void setSaldoAtual(BigDecimal saldoAtual) {this.saldoAtual = saldoAtual;}

    public Long getUsuarioId() {return usuarioId;}
    public void setUsuarioId(Long usuarioId) {this.usuarioId = usuarioId;}

    public TipoConta getTipoconta() {return tipoconta;}
    public void setTipoconta(TipoConta tipoconta) {this.tipoconta = tipoconta;}
}
