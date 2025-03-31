package com.example.pos.academia.dto;

import com.example.pos.academia.entity.Discipline;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineDTO {

    @Id
    private String cod;
    private CadreDidacticeDTO titular;
    private String numeDisciplina;
    private int anStudiu;
    private String tipDisciplina;
    private String categorieDisciplina;
    private String tipExaminare;

    public DisciplineDTO(Discipline discipline) {
        this.cod = discipline.getCod();
        this.titular = new CadreDidacticeDTO(discipline.getTitular());
        this.numeDisciplina = discipline.getNumeDisciplina();
        this.anStudiu = discipline.getAnStudiu();
        this.tipDisciplina = discipline.getTipDisciplina().name();
        this.categorieDisciplina = discipline.getCategorieDisciplina().name();
        this.tipExaminare = discipline.getTipExaminare().name();

    }
}
