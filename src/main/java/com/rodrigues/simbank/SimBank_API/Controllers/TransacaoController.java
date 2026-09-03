package com.rodrigues.simbank.SimBank_API.Controllers;

import com.rodrigues.simbank.SimBank_API.DTO.DepositoRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.SaqueRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.TransacaoResponseDTO;
import com.rodrigues.simbank.SimBank_API.DTO.TransferenciaRequestDTO;
import com.rodrigues.simbank.SimBank_API.Services.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/deposito")
    public TransacaoResponseDTO deposito(@RequestBody DepositoRequestDTO deposito){
        return transacaoService.depositar(deposito);
    }

    @PostMapping("/saque")
    public TransacaoResponseDTO saque(@RequestBody SaqueRequestDTO saque){
        return transacaoService.sacar(saque);
    }

    @PostMapping("/transferir")
    public TransacaoResponseDTO transferir(@RequestBody TransferenciaRequestDTO transfere){ return transacaoService.transferir(transfere);}

}
