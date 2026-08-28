package com.rodrigues.simbank.SimBank_API.Repositories;

import com.rodrigues.simbank.SimBank_API.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
