package com.dbserver.desafiovotacao.domain.service;

import com.dbserver.desafiovotacao.api.model.input.AssociadoInput;
import com.dbserver.desafiovotacao.domain.model.Associado;
import com.dbserver.desafiovotacao.domain.repository.AssociadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AssociadoServiceTest {

    @Mock
    private AssociadoRepository associadoRepository;

    @InjectMocks
    private AssociadoService associadoService;

    @Test
    void buscarAssociadoPeloIdComSucesso() {

        Associado associado = new Associado();
        associado.setId(1L);
        when(associadoRepository.findById(1L)).thenReturn(Optional.of(associado));

        Associado resultado = associadoService.buscarAssociadoPeloId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void salvarAssociadoComSucesso() {

        AssociadoInput associadoInput = new AssociadoInput();
        associadoInput.setCpf("12345678900");

        Associado associado = new Associado();
        associado.setCpf("12345678900");
        when(associadoRepository.save(any(Associado.class))).thenReturn(associado);

        Associado resultado = associadoService.salvarAssociado(associadoInput);

        assertNotNull(resultado);
        assertEquals("12345678900", resultado.getCpf());
    }
}
