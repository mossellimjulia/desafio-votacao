package com.dbserver.desafiovotacao.api.controller;

import com.dbserver.desafiovotacao.api.model.input.VotoInput;
import com.dbserver.desafiovotacao.api.v1.controller.VotoController;
import com.dbserver.desafiovotacao.domain.exceptions.AssociadoNaoEncontradoException;
import com.dbserver.desafiovotacao.domain.exceptions.GlobalExceptionHandler;
import com.dbserver.desafiovotacao.domain.exceptions.PautaNaoEncontradaException;
import com.dbserver.desafiovotacao.domain.exceptions.VotoInvalidoException;
import com.dbserver.desafiovotacao.domain.service.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VotoController.class)
@ExtendWith(MockitoExtension.class)
@Import(GlobalExceptionHandler.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotoService votoService;

    @Test
    void salvarVotoComSucesso() throws Exception {
        VotoInput votoInput = new VotoInput();
        votoInput.setPautaId(1L);
        votoInput.setAssociadoId(1L);
        votoInput.setVoto("sim");

        when(votoService.salvarVoto(votoInput)).thenReturn("Voto registrado com sucesso!");

        mockMvc.perform(post("/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(votoInput)))
                .andExpect(status().isOk())
                .andExpect(content().string("Voto registrado com sucesso!"));

        verify(votoService, times(1)).salvarVoto(votoInput);
    }

    @Test
    void salvarVoto_VotoInvalido() throws Exception {
        VotoInput votoInput = new VotoInput();
        votoInput.setPautaId(1L);
        votoInput.setAssociadoId(1L);
        votoInput.setVoto("invalid");

        when(votoService.salvarVoto(votoInput)).thenThrow(new VotoInvalidoException("Voto inválido! A opção deve ser 'sim' ou 'nao'."));

        mockMvc.perform(post("/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(votoInput)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Voto inválido! A opção deve ser 'sim' ou 'nao'."));
    }

    @Test
    void salvarVoto_AssociadoNaoEncontrado() throws Exception {
        VotoInput votoInput = new VotoInput();
        votoInput.setPautaId(1L);
        votoInput.setAssociadoId(999L);
        votoInput.setVoto("sim");

        when(votoService.salvarVoto(votoInput)).thenThrow(new AssociadoNaoEncontradoException(999L));

        mockMvc.perform(post("/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(votoInput)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Associado não encontrado: 999"));
    }

    @Test
    void salvarVoto_PautaNaoEncontrada() throws Exception {
        VotoInput votoInput = new VotoInput();
        votoInput.setPautaId(1L);
        votoInput.setAssociadoId(1L);
        votoInput.setVoto("sim");

        when(votoService.salvarVoto(votoInput)).thenThrow(new PautaNaoEncontradaException(1L));

        mockMvc.perform(post("/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(votoInput)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Pauta não encontrada com o Id: 1"));
    }
}