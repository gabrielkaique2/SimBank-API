package com.rodrigues.simbank.SimBank_API.Entities;

import com.rodrigues.simbank.SimBank_API.Utilidades.TipoConta;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rowid;

    @Column()
    private String numeroConta;
    private BigDecimal saldoAtual;

    @JoinColumn(name ="usuario_rowid")
    private Long usuarioId;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    public Conta() {
    }

    public Conta(Long rowid, String numeroConta, BigDecimal saldoAtual, Long usuarioId, TipoConta tipoConta) {
        this.rowid = rowid;
        this.numeroConta = numeroConta;
        this.saldoAtual = saldoAtual;
        this.usuarioId = usuarioId;
        this.tipoConta = tipoConta;
    }

    public Long getRowid() {
        return rowid;
    }

    public void setRowid(Long rowid) {
        this.rowid = rowid;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(BigDecimal saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }
}
