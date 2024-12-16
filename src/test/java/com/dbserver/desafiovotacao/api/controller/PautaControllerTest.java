package com.dbserver.desafiovotacao.api.controller;

import com.dbserver.desafiovotacao.api.v1.controller.PautaController;
import com.dbserver.desafiovotacao.domain.service.PautaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PautaController.class)
@ExtendWith(MockitoExtension.class)
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PautaService pautaService;

    @InjectMocks
    private PautaController pautaController;

    @Test
    void listarPautasComSucesso() throws Exception {
        mockMvc.perform(get("/v1/pautas"))
                .andExpect(status().isOk());
    }

    @Test
    void contabilizarVotosComSucesso() throws Exception {
        when(pautaService.contabilizarVotos(1L)).thenReturn("Resultado da votação: SIM = 5, NÃO = 3");

        mockMvc.perform(get("/v1/pautas/resultado/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Resultado da votação: SIM = 5, NÃO = 3"));
    }
}

