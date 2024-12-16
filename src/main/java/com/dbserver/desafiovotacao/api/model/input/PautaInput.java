package com.dbserver.desafiovotacao.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PautaInput {

    @NotBlank
    private String descricao;
}
