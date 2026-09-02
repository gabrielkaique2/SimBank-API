package com.rodrigues.simbank.SimBank_API.Controllers;

import com.rodrigues.simbank.SimBank_API.DTO.ContaRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.ContaResponseDTO;
import com.rodrigues.simbank.SimBank_API.Exception.ResourceNotFoundException;
import com.rodrigues.simbank.SimBank_API.Services.ContaService;
import com.rodrigues.simbank.SimBank_API.Utilidades.TipoConta;
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

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ContaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContaService contaService;

    @InjectMocks
    private ContaController controller;

    private final JsonMapper jsonMapper = new JsonMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new JacksonJsonHttpMessageConverter(jsonMapper))
                .build();
    }

    @Test
    void deveCriarConta() throws Exception {
        ContaRequestDTO request = new ContaRequestDTO();
        request.setUsuarioId(1L);
        request.setTipoconta(TipoConta.C_CORRENTE);

        ContaResponseDTO response = new ContaResponseDTO(10L, "123456", new BigDecimal("2500.00"), 1L, TipoConta.C_CORRENTE);
        when(contaService.criarConta(any(ContaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/contas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rowid").value(10))
                .andExpect(jsonPath("$.numeroConta").value("123456"))
                .andExpect(jsonPath("$.saldoAtual").value(2500.00))
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.tipoconta").value("C_CORRENTE"));

        verify(contaService).criarConta(argThat(conta ->
                conta.getUsuarioId().equals(1L)
                        && conta.getTipoconta() == TipoConta.C_CORRENTE));
        verifyNoMoreInteractions(contaService);
    }

    @Test
    void deveListarContas() throws Exception {
        ContaResponseDTO conta = new ContaResponseDTO(10L, "123456", new BigDecimal("2500.00"), 1L, TipoConta.C_CORRENTE);
        when(contaService.obterContas()).thenReturn(List.of(conta));

        mockMvc.perform(get("/api/contas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rowid").value(10))
                .andExpect(jsonPath("$[0].numeroConta").value("123456"))
                .andExpect(jsonPath("$[0].saldoAtual").value(2500.00))
                .andExpect(jsonPath("$[0].usuarioId").value(1))
                .andExpect(jsonPath("$[0].tipoconta").value("C_CORRENTE"));

        verify(contaService).obterContas();
    }

    @Test
    void deveObterContaPorId() throws Exception {
        ContaResponseDTO conta = new ContaResponseDTO(10L, "123456", new BigDecimal("2500.00"), 1L, TipoConta.C_POUPANCA);
        when(contaService.obterUmaConta(10L)).thenReturn(conta);

        mockMvc.perform(get("/api/contas/{rowid}", 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rowid").value(10))
                .andExpect(jsonPath("$.numeroConta").value("123456"))
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.tipoconta").value("C_POUPANCA"));

        verify(contaService).obterUmaConta(10L);
    }

    @Test
    void deveAtualizarConta() throws Exception {
        ContaRequestDTO request = new ContaRequestDTO();
        request.setUsuarioId(1L);
        request.setTipoconta(TipoConta.C_POUPANCA);

        ContaResponseDTO response = new ContaResponseDTO(10L, "123456", new BigDecimal("3000.00"), 1L, TipoConta.C_POUPANCA);
        when(contaService.atualizarConta(eq(10L), any(ContaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/contas/{rowid}", 10L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rowid").value(10))
                .andExpect(jsonPath("$.saldoAtual").value(3000.00))
                .andExpect(jsonPath("$.tipoconta").value("C_POUPANCA"));

        verify(contaService).atualizarConta(eq(10L), argThat(conta ->
                conta.getUsuarioId().equals(1L)
                        && conta.getTipoconta() == TipoConta.C_POUPANCA));
    }

    @Test
    void deveDeletarConta() throws Exception {
        mockMvc.perform(delete("/api/contas/{rowid}", 10L))
                .andExpect(status().isNoContent());

        verify(contaService).deletarConta(10L);
    }

    @Test
    void deveRetornar404QuandoContaNaoExiste() throws Exception {
        when(contaService.obterUmaConta(99L))
                .thenThrow(new ResourceNotFoundException("Conta nao encontrada"));

        mockMvc.perform(get("/api/contas/{rowid}", 99L))
                .andExpect(status().isNotFound());

        verify(contaService).obterUmaConta(99L);
    }

    @Test
    void deveRetornar400QuandoJsonDaContaForInvalido() throws Exception {
        mockMvc.perform(post("/api/contas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\":}"))
                .andExpect(status().isBadRequest());

        verify(contaService, never()).criarConta(any(ContaRequestDTO.class));
    }
}
