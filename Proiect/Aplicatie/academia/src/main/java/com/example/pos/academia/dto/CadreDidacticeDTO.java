package com.example.pos.academia.dto;


import com.example.pos.academia.entity.CadreDidactice;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CadreDidacticeDTO {
    @Id
    private Long id;
    private String nume;
    private String prenume;
    private String email;
    private String gradDidactic;
    private String tipAsociere;
    private String afiliere;

    public CadreDidacticeDTO(CadreDidactice cadreDidactice) {
        this.id = cadreDidactice.getId();
        this.nume = cadreDidactice.getNume();
        this.prenume = cadreDidactice.getPrenume();
        this.email = cadreDidactice.getEmail();
        this.gradDidactic = cadreDidactice.getGradDidactic().name();
        this.tipAsociere = cadreDidactice.getTipAsociere().name();
        this.afiliere = cadreDidactice.getAfiliere();
    }
}
