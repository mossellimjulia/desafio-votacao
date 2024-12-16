package com.dbserver.desafiovotacao.api.v1.controller;

import com.dbserver.desafiovotacao.api.model.input.VotoInput;
import com.dbserver.desafiovotacao.domain.service.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/votos")
@Tag(name = "VotoController")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @Operation(summary = "Salvar voto", description = "Permite que um associado salve seu voto em uma pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voto salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao registrar voto. Associado já votou ou outro erro"),
            @ApiResponse(responseCode = "404", description = "Pauta ou Associado não encontrado")

    })
    @PostMapping
    public String salvarVoto(@RequestBody @Valid VotoInput votoInput) {
        return votoService.salvarVoto(votoInput);
    }

}
