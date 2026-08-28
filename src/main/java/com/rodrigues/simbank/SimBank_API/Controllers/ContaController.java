package com.rodrigues.simbank.SimBank_API.Controllers;

import com.rodrigues.simbank.SimBank_API.DTO.ContaRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.ContaResponseDTO;
import com.rodrigues.simbank.SimBank_API.Entities.Conta;
import com.rodrigues.simbank.SimBank_API.Exception.ResourceNotFoundException;
import com.rodrigues.simbank.SimBank_API.Services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ContaResponseDTO criarConta(@RequestBody ContaRequestDTO conta) {
        return contaService.criarConta(conta);
    }

    @GetMapping
    public List<ContaResponseDTO> obterContas() {
        return contaService.obterContas();
    }

    @GetMapping(path = "/{rowid}")
    public ResponseEntity<ContaResponseDTO> obterUmaConta(@PathVariable Long rowid) {
        return ResponseEntity.ok(contaService.obterUmaConta(rowid));
    }

    @DeleteMapping("/{rowid}")
    public ResponseEntity<Void> deletaConta(@PathVariable Long rowid) {
        contaService.deletarConta(rowid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{rowid}")
        public ResponseEntity<ContaResponseDTO> atualizarConta(@PathVariable Long rowid, @RequestBody ContaRequestDTO contaDetails){
        return ResponseEntity.ok(contaService.atualizarConta(rowid, contaDetails));
    }
}
