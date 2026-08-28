package com.rodrigues.simbank.SimBank_API.Repositories;

import com.rodrigues.simbank.SimBank_API.Entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    @Query("SELECT MAX(c.rowid) FROM Conta c")
    Long buscarMaiorRowid();
}
