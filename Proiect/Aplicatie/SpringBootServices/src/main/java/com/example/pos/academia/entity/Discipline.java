package com.example.pos.academia.entity;


import com.example.pos.academia.dto.DisciplineDTO;
import com.example.pos.academia.enums.CategorieDisciplina;
import com.example.pos.academia.enums.TipAsociere;
import com.example.pos.academia.enums.TipDisciplina;
import com.example.pos.academia.enums.TipExaminare;
import com.example.pos.academia.services.CadreDidacticeService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="discipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Discipline {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String cod;

    @ManyToOne
    @JoinColumn(name="ID_titular", referencedColumnName = "ID" , nullable = false)
    private CadreDidactice titular;

    @Column(nullable = false)
    private String numeDisciplina;

    @Column(nullable = false)
    private int anStudiu;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipDisciplina tipDisciplina;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategorieDisciplina categorieDisciplina;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipExaminare tipExaminare;

    public Discipline(DisciplineDTO disciplineDTO) {
        this.cod = disciplineDTO.getCod();
        this.titular = new CadreDidactice(disciplineDTO.getTitular());
        this.anStudiu = disciplineDTO.getAnStudiu();
        this.numeDisciplina = disciplineDTO.getNumeDisciplina();
        this.tipDisciplina = TipDisciplina.valueOf(disciplineDTO.getTipDisciplina());
        this.categorieDisciplina = CategorieDisciplina.valueOf(disciplineDTO.getCategorieDisciplina());
        this.tipExaminare = TipExaminare.valueOf(disciplineDTO.getTipExaminare());


    }
}
