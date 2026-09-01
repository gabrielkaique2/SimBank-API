package com.rodrigues.simbank.SimBank_API.Repositories;

import com.rodrigues.simbank.SimBank_API.Entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
