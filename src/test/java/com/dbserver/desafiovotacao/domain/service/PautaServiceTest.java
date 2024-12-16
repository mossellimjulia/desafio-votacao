package com.dbserver.desafiovotacao.domain.service;

import com.dbserver.desafiovotacao.api.model.input.PautaInput;
import com.dbserver.desafiovotacao.domain.model.Pauta;
import com.dbserver.desafiovotacao.domain.repository.PautaRepository;
import com.dbserver.desafiovotacao.domain.repository.VotoRepository;
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
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotoRepository votoRepository;

    @InjectMocks
    private PautaService pautaService;

    @Test
    void salvarPautaComSucesso() {

        PautaInput pautaInput = new PautaInput();
        pautaInput.setDescricao("Nova pauta");

        Pauta pauta = new Pauta();
        pauta.setDescricao("Nova pauta");
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        Pauta resultado = pautaService.salvarPauta(pautaInput);

        assertNotNull(resultado);
        assertEquals("Nova pauta", resultado.getDescricao());
    }

    @Test
    void contabilizarVotosComSucesso() {

        long pautaId = 1L;

        Pauta pauta = new Pauta();
        pauta.setId(pautaId);
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));

        when(votoRepository.countByPautaIdAndVoto(pautaId, "sim")).thenReturn(5L);
        when(votoRepository.countByPautaIdAndVoto(pautaId, "nao")).thenReturn(3L);

        String resultado = pautaService.contabilizarVotos(pautaId);

        assertEquals("Resultado da votação: SIM = 5, NÃO = 3", resultado);
    }
}
