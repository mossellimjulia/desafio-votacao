package com.dbserver.desafiovotacao.api.v1.controller;

import com.dbserver.desafiovotacao.api.model.input.AssociadoInput;
import com.dbserver.desafiovotacao.domain.model.Associado;
import com.dbserver.desafiovotacao.domain.service.AssociadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/associados")
@Tag(name = "AssociadosController")
public class AssociadoController {

    @Autowired
    private AssociadoService associadoService;


    @Operation(summary = "Buscar associado por ID", description = "Busca associado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associado encontrado"),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado")
    })
    @GetMapping("/associadoId/{id}")
    public Associado buscarAssociadoPeloId(@PathVariable Long id) {
        return associadoService.buscarAssociadoPeloId(id);
    }

    @Operation(summary = "Listar todos os associados", description = "Retorna lista de todos os associados")
    @GetMapping
    public List<Associado> listarAssociados() {
        return associadoService.listarAssociados();
    }

    @Operation(summary = "Salvar associado", description = "Salva um novo associado no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associado salvo com sucesso")
    })
    @PostMapping
    public Associado salvarAssociado(@RequestBody @Valid AssociadoInput associadoInput) {
        return associadoService.salvarAssociado(associadoInput);
    }
}
