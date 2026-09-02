package com.rodrigues.simbank.SimBank_API.Controllers;

import com.rodrigues.simbank.SimBank_API.DTO.UsuarioRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.UsuarioResponseDTO;
import com.rodrigues.simbank.SimBank_API.Exception.ResourceNotFoundException;
import com.rodrigues.simbank.SimBank_API.Services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController controller;

    private final JsonMapper jsonMapper = new JsonMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new JacksonJsonHttpMessageConverter(jsonMapper))
                .build();
    }

    @Test
    void deveCriarUsuario() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setNome("Gabriel");
        request.setCpf("12345678900");
        request.setEmail("gabriel@email.com");
        request.setSenha("123456");

        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Gabriel", "12345678900", "gabriel@email.com");
        when(usuarioService.criarUsuario(any(UsuarioRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rowid").value(1))
                .andExpect(jsonPath("$.nome").value("Gabriel"))
                .andExpect(jsonPath("$.cpf").value("12345678900"))
                .andExpect(jsonPath("$.email").value("gabriel@email.com"));

        verify(usuarioService).criarUsuario(argThat(usuario ->
                "Gabriel".equals(usuario.getNome())
                        && "12345678900".equals(usuario.getCpf())
                        && "gabriel@email.com".equals(usuario.getEmail())
                        && "123456".equals(usuario.getSenha())));
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void deveListarUsuarios() throws Exception {
        UsuarioResponseDTO usuario = new UsuarioResponseDTO(1L, "Gabriel", "12345678900", "gabriel@email.com");
        when(usuarioService.obterUsuarios()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rowid").value(1))
                .andExpect(jsonPath("$[0].nome").value("Gabriel"))
                .andExpect(jsonPath("$[0].cpf").value("12345678900"))
                .andExpect(jsonPath("$[0].email").value("gabriel@email.com"));

        verify(usuarioService).obterUsuarios();
    }

    @Test
    void deveObterUsuarioPorId() throws Exception {
        UsuarioResponseDTO usuario = new UsuarioResponseDTO(1L, "Gabriel", "12345678900", "gabriel@email.com");
        when(usuarioService.obterUmUsuario(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/{rowid}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rowid").value(1))
                .andExpect(jsonPath("$.nome").value("Gabriel"))
                .andExpect(jsonPath("$.cpf").value("12345678900"))
                .andExpect(jsonPath("$.email").value("gabriel@email.com"));

        verify(usuarioService).obterUmUsuario(1L);
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setNome("Gabriel Atualizado");
        request.setCpf("00000000000");
        request.setEmail("novo@email.com");
        request.setSenha("654321");

        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Gabriel Atualizado", "00000000000", "novo@email.com");
        when(usuarioService.atualizarUsuario(eq(1L), any(UsuarioRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/usuarios/{rowid}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.rowid").value(1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Gabriel Atualizado"))
                .andExpect(jsonPath("$.email").value("novo@email.com"));

        verify(usuarioService).atualizarUsuario(eq(1L), argThat(usuario ->
                "Gabriel Atualizado".equals(usuario.getNome())
                        && "00000000000".equals(usuario.getCpf())
                        && "novo@email.com".equals(usuario.getEmail())
                        && "654321".equals(usuario.getSenha())));
    }

    @Test
    void deveDeletarUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuarios/{rowid}", 1L))
                .andExpect(status().isNoContent());

        verify(usuarioService).deletarUsuario(1L);
    }

    @Test
    void deveRetornar404QuandoUsuarioNaoExiste() throws Exception {
        when(usuarioService.obterUmUsuario(99L))
                .thenThrow(new ResourceNotFoundException("Usuario nao encontrado"));

        mockMvc.perform(get("/api/usuarios/{rowid}", 99L))
                .andExpect(status().isNotFound());

        verify(usuarioService).obterUmUsuario(99L);
    }

    @Test
    void deveRetornar400QuandoJsonDoUsuarioForInvalido() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":}"))
                .andExpect(status().isBadRequest());

        verify(usuarioService, never()).criarUsuario(any(UsuarioRequestDTO.class));
    }
}
