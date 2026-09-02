package com.rodrigues.simbank.SimBank_API.Controllers;

import com.rodrigues.simbank.SimBank_API.DTO.DepositoRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.SaqueRequestDTO;
import com.rodrigues.simbank.SimBank_API.DTO.TransacaoResponseDTO;
import com.rodrigues.simbank.SimBank_API.Services.TransacaoService;
import com.rodrigues.simbank.SimBank_API.Utilidades.TipoTransacao;
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
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private TransacaoController controller;

    private final JsonMapper jsonMapper = new JsonMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new JacksonJsonHttpMessageConverter(jsonMapper))
                .build();
    }

    @Test
    void deveDepositar() throws Exception {
        DepositoRequestDTO request = new DepositoRequestDTO(new BigDecimal("100.00"), 1L);
        TransacaoResponseDTO response = new TransacaoResponseDTO(1L, TipoTransacao.DEPOSITO, new BigDecimal("100.00"), Instant.now(), null, 1L);
        when(transacaoService.depositar(any(DepositoRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/transacoes/deposito")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rowid").value(1))
                .andExpect(jsonPath("$.tipoTransacao").value("DEPOSITO"))
                .andExpect(jsonPath("$.valor").value(100.00))
                .andExpect(jsonPath("$.contaDestinoId").value(1));

        verify(transacaoService).depositar(argThat(deposito ->
                new BigDecimal("100.00").compareTo(deposito.getValor()) == 0
                        && deposito.getContaDestinoId().equals(1L)));
    }

    @Test
    void deveSacar() throws Exception {
        SaqueRequestDTO request = new SaqueRequestDTO(new BigDecimal("80.00"), 1L);
        TransacaoResponseDTO response = new TransacaoResponseDTO(2L, TipoTransacao.SAQUE, new BigDecimal("80.00"), Instant.now(), 1L, null);
        when(transacaoService.sacar(any(SaqueRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/transacoes/saque")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rowid").value(2))
                .andExpect(jsonPath("$.tipoTransacao").value("SAQUE"))
                .andExpect(jsonPath("$.valor").value(80.00))
                .andExpect(jsonPath("$.contaOrigemId").value(1));

        verify(transacaoService).sacar(argThat(saque ->
                new BigDecimal("80.00").compareTo(saque.getValor()) == 0
                        && saque.getContaOrigemId().equals(1L)));
    }

    @Test
    void deveRetornar400QuandoJsonDoDepositoForInvalido() throws Exception {
        mockMvc.perform(post("/api/transacoes/deposito")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"valor\":}"))
                .andExpect(status().isBadRequest());

        verify(transacaoService, never()).depositar(any(DepositoRequestDTO.class));
    }
}
