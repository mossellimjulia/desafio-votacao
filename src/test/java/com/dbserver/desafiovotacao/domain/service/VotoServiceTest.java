package com.dbserver.desafiovotacao.domain.service;

import com.dbserver.desafiovotacao.api.model.input.VotoInput;
import com.dbserver.desafiovotacao.domain.exceptions.AssociadoNaoEncontradoException;
import com.dbserver.desafiovotacao.domain.exceptions.PautaNaoEncontradaException;
import com.dbserver.desafiovotacao.domain.exceptions.VotoInvalidoException;
import com.dbserver.desafiovotacao.domain.model.Associado;
import com.dbserver.desafiovotacao.domain.model.Pauta;
import com.dbserver.desafiovotacao.domain.repository.AssociadoRepository;
import com.dbserver.desafiovotacao.domain.repository.PautaRepository;
import com.dbserver.desafiovotacao.domain.repository.VotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class VotoServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private AssociadoRepository associadoRepository;

    @InjectMocks
    private VotoService votoService;

    @Test
    void salvarVotoComSucesso() {
        VotoInput votoInput = new VotoInput();
        votoInput.setPautaId(1L);
        votoInput.setAssociadoId(1L);
        votoInput.setVoto("sim");

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(new Pauta()));
        when(associadoRepository.findById(1L)).thenReturn(Optional.of(new Associado()));
        when(votoRepository.findByPautaIdAndAssociadoId(1L, 1L)).thenReturn(Optional.empty());

        String resultado = votoService.salvarVoto(votoInput);

        assertEquals("Voto registrado com sucesso!", resultado);
    }

    @Test
    void salvarVoto_VotoInvalido() {
        VotoInput votoInput = new VotoInput();
        votoInput.setPautaId(1L);
        votoInput.setAssociadoId(1L);
        votoInput.setVoto("invalid");

        VotoInvalidoException exception = assertThrows(VotoInvalidoException.class, () -> {
            votoService.salvarVoto(votoInput);
        });

        assertEquals("Voto inválido! A opção deve ser 'sim' ou 'nao'.", exception.getMessage());
    }

    @Test
    public void salvarVoto_AssociadoNaoEncontrado() {

        Long pautaId = 1L;
        Long associadoId = 999L;

        Pauta pauta = new Pauta();
        pauta.setId(pautaId);
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));

        when(associadoRepository.findById(associadoId)).thenReturn(Optional.empty());

        VotoInput votoInput = new VotoInput();
        votoInput.setPautaId(pautaId);
        votoInput.setAssociadoId(associadoId);
        votoInput.setVoto("sim");

        assertThrows(AssociadoNaoEncontradoException.class, () -> {
            votoService.salvarVoto(votoInput);
        });
    }

    @Test
    void salvarVoto_PautaNaoEncontrada() {
        VotoInput votoInput = new VotoInput();
        votoInput.setPautaId(1L);
        votoInput.setAssociadoId(1L);
        votoInput.setVoto("sim");

        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        PautaNaoEncontradaException exception = assertThrows(PautaNaoEncontradaException.class, () -> {
            votoService.salvarVoto(votoInput);
        });

        assertEquals("Pauta não encontrada com o Id: 1", exception.getMessage());
    }
}

