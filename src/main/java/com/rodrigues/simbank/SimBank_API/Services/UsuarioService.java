package com.rodrigues.simbank.SimBank_API.Services;

import com.rodrigues.simbank.SimBank_API.DTO.UsuarioRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.UsuarioResponseDTO;
import com.rodrigues.simbank.SimBank_API.Entities.Usuario;
import com.rodrigues.simbank.SimBank_API.Exception.ResourceNotFoundException;
import com.rodrigues.simbank.SimBank_API.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponseDTO(salvo);
    }

    public List<UsuarioResponseDTO> obterUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO obterUmUsuario(Long rowid) {
        Usuario usuario = usuarioRepository.findById(rowid)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado com o ID" + rowid));
        return toResponseDTO(usuario);
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
        usuario.setSenha(dto.getSenha());

        Usuario atualizado = usuarioRepository.save(usuario);
        return toResponseDTO(atualizado);
    }

    // metodo privado de conversão Entity -> DTO, reaproveitado em todos lugar
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getRowid(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail()
        );

    }
}
