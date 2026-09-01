package com.rodrigues.simbank.SimBank_API.DTO;

import java.math.BigDecimal;
import java.time.Instant;
import com.rodrigues.simbank.SimBank_API.Utilidades.TipoTransacao;

public class TransacaoResponseDTO {

    private Long rowid;
    private TipoTransacao tipoTransacao;
    private BigDecimal valor;
    private Instant timeStamp;
    private Long contaOrigemId;
    private Long contaDestinoId;

    public TransacaoResponseDTO() {
    }

    public TransacaoResponseDTO(Long rowid, TipoTransacao tipoTransacao, BigDecimal valor, Instant timeStamp,
                                Long contaOrigemId, Long contaDestinoId) {
        this.rowid = rowid;
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.timeStamp = timeStamp;
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
    }

    public Long getRowid() {
        return rowid;
    }

    public void setRowid(Long rowid) {
        this.rowid = rowid;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getContaOrigemId() {
        return contaOrigemId;
    }

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
