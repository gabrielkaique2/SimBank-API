package com.rodrigues.simbank.SimBank_API.Services;

import com.rodrigues.simbank.SimBank_API.DTO.ContaRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.ContaResponseDTO;
import com.rodrigues.simbank.SimBank_API.Entities.Conta;
import com.rodrigues.simbank.SimBank_API.Exception.ResourceNotFoundException;
import com.rodrigues.simbank.SimBank_API.Repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public ContaResponseDTO criarConta(ContaRequestDTO dto){
        Conta conta = new Conta();
        conta.setTipoConta(dto.getTipoconta());
        conta.setUsuarioId(dto.getUsuarioId());
        conta.setNumeroConta(gerarNumeroConta());
        conta.setSaldoAtual(BigDecimal.ZERO);

        Conta salvo = contaRepository.save(conta);
        return toResponseDTO(salvo);
    }

    public List<ContaResponseDTO> obterContas() {
        return contaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ContaResponseDTO obterUmaConta(Long rowid){
        Conta conta = contaRepository.findById(rowid)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrado com o ID" + rowid));
        return toResponseDTO(conta);
    }

    public void deletarConta(Long rowid) {
        Conta conta = contaRepository.findById(rowid)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrado com o ID" + rowid));
        contaRepository.delete(conta);
    }

    public ContaResponseDTO atualizarConta(Long rowid, ContaRequestDTO dto){
        Conta conta = contaRepository.findById(rowid)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrado com o ID" + rowid));
        conta.setUsuarioId(dto.getUsuarioId());
        conta.setTipoConta(dto.getTipoconta());

        Conta atualizada = contaRepository.save(conta);
        return toResponseDTO(atualizada);
    }

    private ContaResponseDTO toResponseDTO(Conta conta){
        return new ContaResponseDTO(
                conta.getRowid(),
                conta.getNumeroConta(),
                conta.getSaldoAtual(),
                conta.getUsuarioId(),
                conta.getTipoConta());
    }

    private String gerarNumeroConta(){
        Long maiorRowId = contaRepository.buscarMaiorRowid();
        Long proximoNumeroContaValido = (maiorRowId == null ? 0L : maiorRowId) + 1;
        return String.format("%08d", proximoNumeroContaValido);
    }

}
