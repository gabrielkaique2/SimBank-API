package com.rodrigues.simbank.SimBank_API.Services;

import com.rodrigues.simbank.SimBank_API.DTO.UsuarioRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.UsuarioResponseDTO;
import com.rodrigues.simbank.SimBank_API.Entities.Usuario;
import com.rodrigues.simbank.SimBank_API.Exception.ResourceNotFoundException;
import com.rodrigues.simbank.SimBank_API.Repositories.UsuarioRepository;
import com.rodrigues.simbank.SimBank_API.Config.SecurityConfig;

import com.rodrigues.simbank.SimBank_API.Utilidades.TipoRole;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setRole(TipoRole.ROLE_USER);

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponseDTO(salvo);
    }

    public List<UsuarioResponseDTO> obterUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO obterUmUsuario(Long rowid, String emailAutenticado, Collection<? extends GrantedAuthority> roles) {
        Usuario usuario = usuarioRepository.findById(rowid)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado com o ID" + rowid));

        boolean mesmoEmail = emailAutenticado.equals(usuario.getEmail());

        if(mesmoEmail){
            return toResponseDTO(usuario);
        }
        for(GrantedAuthority role:roles){
            if(role.getAuthority().equals("ROLE_ADMIN")){
                return toResponseDTO(usuario);
            }
        }
        throw new AccessDeniedException("Acesso Negado");
    }

    public void deletarUsuario(Long rowid) {
        Usuario usuario = usuarioRepository.findById(rowid)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado com o ID" + rowid));
        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long rowid, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(rowid)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado com o ID" + rowid));

        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setNome(dto.getNome());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario atualizado = usuarioRepository.save(usuario);
        return toResponseDTO(atualizado);
    }

    // metodo privado de conversão Entity -> DTO, reaproveitado em todos lugar
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getRowid(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getRole()
        );

    }
}
