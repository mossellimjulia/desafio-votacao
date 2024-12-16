package com.dbserver.desafiovotacao.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String voto;
    @ManyToOne
    @JoinColumn(name = "pauta_id")
    @JsonBackReference
    private Pauta pauta;
    @ManyToOne
    @JoinColumn(name = "associado_id")
    @JsonIgnore
    private Associado associado;

    public boolean getVotoSim() {
        return Objects.equals(voto, "sim");
    }

    public boolean getVotoNao() {
        return Objects.equals(voto, "nao");
    }
}
