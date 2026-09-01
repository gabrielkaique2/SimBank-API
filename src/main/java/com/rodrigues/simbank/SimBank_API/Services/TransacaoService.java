package com.rodrigues.simbank.SimBank_API.Services;

import com.rodrigues.simbank.SimBank_API.Entities.Transacao;
import com.rodrigues.simbank.SimBank_API.DTO.TransacaoRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.TransacaoResponseDTO;
import com.rodrigues.simbank.SimBank_API.Repositories.TransacaoRepository;
import com.rodrigues.simbank.SimBank_API.Repositories.ContaRepository;
import com.rodrigues.simbank.SimBank_API.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    //create
    //getAll
    //getOne
    //depositar(?)
    //sacar(?)

}
