package com.example.pos.academia.entity;


import com.example.pos.academia.dto.CadreDidacticeDTO;
import com.example.pos.academia.enums.GradDidactic;
import com.example.pos.academia.enums.TipAsociere;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cadre_didactice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CadreDidactice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nume;

    @Column(nullable = false)
    private String prenume;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private GradDidactic gradDidactic;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipAsociere tipAsociere;

    @Column
    private String afiliere;

    public CadreDidactice(CadreDidacticeDTO cadreDidacticeDTO) {
        this.id = cadreDidacticeDTO.getId();
        this.nume = cadreDidacticeDTO.getNume();
        this.prenume = cadreDidacticeDTO.getPrenume();
        this.email = cadreDidacticeDTO.getEmail();
        this.gradDidactic = GradDidactic.valueOf(cadreDidacticeDTO.getGradDidactic());
        this.tipAsociere = TipAsociere.valueOf(cadreDidacticeDTO.getTipAsociere());
        this.afiliere = cadreDidacticeDTO.getAfiliere();
    }
}
