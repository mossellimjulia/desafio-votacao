package com.dbserver.desafiovotacao.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pauta {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String descricao;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataEncerramento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoPauta situacaoPauta;

    @OneToMany(mappedBy = "pauta")
    @Builder.Default
    private List<Voto> votos = new ArrayList<>();

}
