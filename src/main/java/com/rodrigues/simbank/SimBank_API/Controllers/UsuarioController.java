package com.rodrigues.simbank.SimBank_API.Controllers;

import com.rodrigues.simbank.SimBank_API.DTO.UsuarioRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.UsuarioResponseDTO;
import com.rodrigues.simbank.SimBank_API.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public UsuarioResponseDTO criarUsuario(@RequestBody UsuarioRequestDTO usuario){
        return  usuarioService.criarUsuario(usuario);
    }

    @GetMapping
    public List<UsuarioResponseDTO> obterUsuarios(){
        return usuarioService.obterUsuarios();
    }

    @GetMapping(path = "/{rowid}")
    public ResponseEntity<UsuarioResponseDTO> obterUmUsuario(@PathVariable Long rowid){
        return ResponseEntity.ok(usuarioService.obterUmUsuario(rowid));
    }

    @DeleteMapping("/{rowid}")
    public ResponseEntity<Void> deletaUsuario(@PathVariable Long rowid){
        usuarioService.deletarUsuario(rowid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{rowid}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long rowid, @RequestBody UsuarioRequestDTO usuarioDetails){
        return ResponseEntity.ok(usuarioService.atualizarUsuario(rowid, usuarioDetails));
    }

}
