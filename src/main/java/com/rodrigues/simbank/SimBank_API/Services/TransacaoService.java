package com.rodrigues.simbank.SimBank_API.Services;

import com.rodrigues.simbank.SimBank_API.DTO.*;
import com.rodrigues.simbank.SimBank_API.Entities.Conta;
import com.rodrigues.simbank.SimBank_API.Entities.Transacao;
import com.rodrigues.simbank.SimBank_API.Repositories.TransacaoRepository;
import com.rodrigues.simbank.SimBank_API.Repositories.ContaRepository;
import com.rodrigues.simbank.SimBank_API.Exception.ResourceNotFoundException;
import com.rodrigues.simbank.SimBank_API.Utilidades.TipoTransacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    public TransacaoResponseDTO depositar(DepositoRequestDTO dto){

        Conta conta = contaRepository.findById(dto.getContaDestinoId())
                .orElseThrow(()-> new ResourceNotFoundException("Conta Não encontrada com o ID "+ dto.getContaDestinoId()));

        BigDecimal saldoAtual = conta.getSaldoAtual();


        if (dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("O valor do depósito deve ser positivo");
        }
        BigDecimal novoSaldo = saldoAtual.add(dto.getValor());

       conta.setSaldoAtual(novoSaldo);
       contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacao.setValor(dto.getValor());
        transacao.setTimeStamp(Instant.now());
        transacao.setContaOrigemId(null);
        transacao.setContaDestinoId(dto.getContaDestinoId());

        Transacao salvar = transacaoRepository.save(transacao);

        TransacaoResponseDTO transacaoResponseDTO = new TransacaoResponseDTO();
        transacaoResponseDTO.setContaDestinoId(salvar.getContaDestinoId());
        transacaoResponseDTO.setContaOrigemId(salvar.getContaOrigemId());
        transacaoResponseDTO.setRowid(salvar.getRowid());
        transacaoResponseDTO.setTimeStamp(salvar.getTimeStamp());
        transacaoResponseDTO.setTipoTransacao(salvar.getTipoTransacao());
        transacaoResponseDTO.setValor(salvar.getValor());
        return transacaoResponseDTO;
    }

    @Transactional
    public TransacaoResponseDTO sacar(SaqueRequestDTO dto){
        Conta conta = contaRepository.findById(dto.getContaOrigemId())
                .orElseThrow(()-> new ResourceNotFoundException("Conta Não encontrada com o ID "+ dto.getContaOrigemId()));

        BigDecimal saldoAtual = conta.getSaldoAtual();
            int resultado = saldoAtual.compareTo(dto.getValor());
            if(resultado < 0){
                throw new RuntimeException("Saldo insuficiente");
            }
            if(dto.getValor().compareTo(BigDecimal.ZERO) <= 0){
                throw new RuntimeException("O valor do saque deve ser positivo");
            }

            BigDecimal novoSaldo = saldoAtual.subtract(dto.getValor());
            conta.setSaldoAtual(novoSaldo);
            contaRepository.save(conta);

            Transacao transacao = new Transacao();
            transacao.setContaOrigemId(dto.getContaOrigemId());
            transacao.setContaDestinoId(null);
            transacao.setTimeStamp(Instant.now());
            transacao.setTipoTransacao(TipoTransacao.SAQUE);
            transacao.setValor(dto.getValor());
            Transacao salvar = transacaoRepository.save(transacao);

            TransacaoResponseDTO transacaoResponseDTO = new TransacaoResponseDTO();
            transacaoResponseDTO.setContaOrigemId(salvar.getContaOrigemId());
            transacaoResponseDTO.setContaDestinoId(salvar.getContaDestinoId());
            transacaoResponseDTO.setRowid(salvar.getRowid());
            transacaoResponseDTO.setTimeStamp(salvar.getTimeStamp());
            transacaoResponseDTO.setTipoTransacao(salvar.getTipoTransacao());
            transacaoResponseDTO.setValor(salvar.getValor());

            return transacaoResponseDTO;

    }

    @Transactional
    public TransacaoResponseDTO transferir(TransferenciaRequestDTO dto) {

        Conta contaOrigem = contaRepository.findById(dto.getContaOrigemId())
                .orElseThrow(()-> new ResourceNotFoundException("Conta Não encontrada com o ID "+ dto.getContaOrigemId()));

        Conta contaDestino = contaRepository.findById(dto.getContaDestinoId())
                .orElseThrow(()-> new ResourceNotFoundException("Conta Não encontrada com o ID "+ dto.getContaDestinoId()));

        BigDecimal saldoDestino = contaDestino.getSaldoAtual();
        BigDecimal saldoOrigem = contaOrigem.getSaldoAtual();
        BigDecimal saldoTransacao = dto.getValor();
        int resultado = saldoOrigem.compareTo(saldoTransacao);
        if(resultado < 0){
            throw new RuntimeException("Saldo insulficiente na conta " + contaOrigem.getRowid());
        }
        if(dto.getValor().compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("O valor da transferencia deve ser positivo");
        }

        BigDecimal novoOrigem = saldoOrigem.subtract(dto.getValor());
        BigDecimal novoDestino = saldoDestino.add(dto.getValor());

        contaOrigem.setSaldoAtual(novoOrigem);
        contaDestino.setSaldoAtual(novoDestino);
        Conta contaOrigemAtualizada = contaRepository.save(contaOrigem);
        Conta contaDestinoAtualizada = contaRepository.save(contaDestino);

        Transacao transacao = new Transacao();
        transacao.setValor(dto.getValor());
        transacao.setTipoTransacao(TipoTransacao.TRANSFERENCIA);
        transacao.setTimeStamp(Instant.now());
        transacao.setContaDestinoId(contaDestinoAtualizada.getRowid());
        transacao.setContaOrigemId(contaOrigemAtualizada.getRowid());
        Transacao salvar = transacaoRepository.save(transacao);

        TransacaoResponseDTO transacaoResponseDTO = new TransacaoResponseDTO();
        transacaoResponseDTO.setContaOrigemId(salvar.getContaOrigemId());
        transacaoResponseDTO.setContaDestinoId(salvar.getContaDestinoId());
        transacaoResponseDTO.setRowid(salvar.getRowid());
        transacaoResponseDTO.setTimeStamp(salvar.getTimeStamp());
        transacaoResponseDTO.setTipoTransacao(salvar.getTipoTransacao());
        transacaoResponseDTO.setValor(salvar.getValor());

        return transacaoResponseDTO;
    }

}
