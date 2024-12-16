package com.dbserver.desafiovotacao.api.v1.controller;

import com.dbserver.desafiovotacao.api.model.input.PautaInput;
import com.dbserver.desafiovotacao.domain.model.Pauta;
import com.dbserver.desafiovotacao.domain.model.SituacaoPauta;
import com.dbserver.desafiovotacao.domain.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pautas")
@Tag(name = "PautaController")
public class PautaController {

    @Autowired
    private PautaService pautaService;


    @Operation(summary = "Listar todas as pautas", description = "Retorna uma lista de todas as pautas disponíveis")
    @GetMapping
    public List<Pauta> listarPautas() {
        return pautaService.listarPautas();
    }

    @Operation(summary = "Resultado da votação", description = "Retorna o resultado da votação de uma pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado da votação da pauta"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
    })
    @GetMapping("/resultado/{pautaId}")
    public String contabilizarVotos(@PathVariable Long pautaId) {
        return pautaService.contabilizarVotos(pautaId);
    }

    @Operation(summary = "Salvar pauta", description = "Salva uma nova pauta no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pauta salva com sucesso")
    })
    @PostMapping
    public Pauta salvarPauta(@RequestBody @Valid PautaInput pautaInput) {
        return pautaService.salvarPauta(pautaInput);
    }

    @Operation(summary = "Abrir sessão de votação", description = "Abre uma sessão de votação para uma pauta específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão de votação aberta com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
    })
    @PutMapping("/abrir-sessao/{pautaId}")
    public String abrirSessaoVotacao(@PathVariable Long pautaId, @RequestParam(defaultValue = "1") int tempoAbertura) {
        return pautaService.abrirSessaoVotacao(pautaId, tempoAbertura);
    }

    @PutMapping("/fechar-sessao/{pautaId}")
    public String fecharSessaoVotacao(@PathVariable Long pautaId) {
        return pautaService.fecharSessaoVotacao(pautaId);
    }

    @Operation(summary = "Consultar situação da pauta",
            description = "Consulta se a pauta esta aberta ao encerrada para votação pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão de votação aberta com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
    })
    @GetMapping("/{pautaId}/situacao")
    public SituacaoPauta consultarSituacaoPauta(@PathVariable Long pautaId) {
        SituacaoPauta situacaoPauta = pautaService.consultarSituacaoPauta(pautaId);
        if (situacaoPauta == null) {
            throw new RuntimeException("Pauta não encontrada");
        }
        return situacaoPauta;
    }

}
