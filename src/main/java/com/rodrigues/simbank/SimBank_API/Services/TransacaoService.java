package com.rodrigues.simbank.SimBank_API.Services;

import com.rodrigues.simbank.SimBank_API.DTO.DepositoRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.SaqueRequestDTO;
import com.rodrigues.simbank.SimBank_API.Entities.Conta;
import com.rodrigues.simbank.SimBank_API.Entities.Transacao;
import com.rodrigues.simbank.SimBank_API.DTO.TransacaoRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.TransacaoResponseDTO;
import com.rodrigues.simbank.SimBank_API.Repositories.TransacaoRepository;
import com.rodrigues.simbank.SimBank_API.Repositories.ContaRepository;
import com.rodrigues.simbank.SimBank_API.Exception.ResourceNotFoundException;
import com.rodrigues.simbank.SimBank_API.Utilidades.TipoTransacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    //depositar(?)
    public TransacaoResponseDTO depositar(DepositoRequestDTO dto){

        // 1. busca a Conta pelo dto.getContaDestinoId()
        Conta conta = contaRepository.findById(dto.getContaDestinoId())
                .orElseThrow(()-> new ResourceNotFoundException("Conta Não encontrada com o ID "+ dto.getContaDestinoId()));

        // 2. soma dto.getValor() ao saldoAtual da conta
        BigDecimal saldoAtual = conta.getSaldoAtual();
        BigDecimal novoSaldo = saldoAtual.add(dto.getValor());


        // 3. salva a conta (contaRepository.save)
       conta.setSaldoAtual(novoSaldo);
       Conta contaAtualizada = contaRepository.save(conta);

        // 4. cria uma nova Transacao (tipoTransacao=DEPOSITO, valor, timeStamp=Instant.now(), contaOrigemId=null, contaDestinoId=X)
        Transacao transacao = new Transacao();
        transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacao.setValor(dto.getValor());
        transacao.setTimeStamp(Instant.now());
        transacao.setContaOrigemId(null);
        transacao.setContaDestinoId(dto.getContaDestinoId());

        // 5. salva a Transacao (transacaoRepository.save)

        Transacao salvar = transacaoRepository.save(transacao);

        // 6. converte pra TransacaoResponseDTO e retorna

        TransacaoResponseDTO transacaoResponseDTO = new TransacaoResponseDTO();
        transacaoResponseDTO.setContaDestinoId(salvar.getContaDestinoId());
        transacaoResponseDTO.setContaOrigemId(salvar.getContaOrigemId());
        transacaoResponseDTO.setRowid(salvar.getRowid());
        transacaoResponseDTO.setTimeStamp(salvar.getTimeStamp());
        transacaoResponseDTO.setTipoTransacao(salvar.getTipoTransacao());
        transacaoResponseDTO.setValor(salvar.getValor());
        return transacaoResponseDTO;
    }

    //sacar(?)
    public TransacaoResponseDTO sacar(SaqueRequestDTO dto){
        // 1. busca a Conta pelo dto.getContaOrigemId()
        Conta conta = contaRepository.findById(dto.getContaOrigemId())
                .orElseThrow(()-> new ResourceNotFoundException("Conta Não encontrada com o ID "+ dto.getContaOrigemId()));

        // 2. valida: conta.getSaldoAtual() é maior ou igual a dto.getValor()?
        //    se não for → lança exception (por enquanto, RuntimeException genérica)
        BigDecimal saldoAtual = conta.getSaldoAtual();

            int resultado = saldoAtual.compareTo(dto.getValor());
            if(resultado < 0){
                throw new RuntimeException("Saldo insuficiente");
            }
            // 3. subtrai dto.getValor() do saldoAtual
            BigDecimal novoSaldo = saldoAtual.subtract(dto.getValor());
            conta.setSaldoAtual(novoSaldo);

            // 4. salva a conta
            Conta contaAtualizada = contaRepository.save(conta);

            // 5. cria e salva uma Transacao (tipo=SAQUE, contaOrigemId=X, contaDestinoId=null)
            Transacao transacao = new Transacao();
            transacao.setContaOrigemId(dto.getContaOrigemId());
            transacao.setContaDestinoId(null);
            transacao.setTimeStamp(Instant.now());
            transacao.setTipoTransacao(TipoTransacao.SAQUE);
            transacao.setValor(dto.getValor());
            Transacao salvar = transacaoRepository.save(transacao);

            // 6. converte pra TransacaoResponseDTO e retorna
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
