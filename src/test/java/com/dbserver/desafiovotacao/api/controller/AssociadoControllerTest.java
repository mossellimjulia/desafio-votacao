package com.dbserver.desafiovotacao.api.controller;

import com.dbserver.desafiovotacao.api.model.input.AssociadoInput;
import com.dbserver.desafiovotacao.api.v1.controller.AssociadoController;
import com.dbserver.desafiovotacao.domain.model.Associado;
import com.dbserver.desafiovotacao.domain.service.AssociadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AssociadoController.class)
@Import(AssociadoService.class)
public class AssociadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssociadoService associadoService;


    @Test
    void testBuscarAssociadoPeloId() throws Exception {
        Associado associado = new Associado();
        associado.setId(1L);
        when(associadoService.buscarAssociadoPeloId(1L)).thenReturn(associado);

        mockMvc.perform(get("/v1/associados/associadoId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSalvarAssociado() throws Exception {
        AssociadoInput associadoInput = new AssociadoInput();
        associadoInput.setCpf("12345678900");

        Associado associado = new Associado();
        associado.setCpf("12345678900");
        when(associadoService.salvarAssociado(associadoInput)).thenReturn(associado);

        mockMvc.perform(post("/v1/associados")
                        .contentType("application/json")
                        .content("{\"cpf\": \"12345678900\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("12345678900"));
    }
}
