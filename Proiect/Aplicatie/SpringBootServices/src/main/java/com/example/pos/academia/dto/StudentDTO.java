package com.example.pos.academia.dto;


import com.example.pos.academia.entity.Student;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    @Id
    private Long id;
    private String nume;
    private String prenume;
    private String email;
    private String cicluStudii;
    private int anStudiu;
    private int grupa;
//Constructor cu entitate
    public StudentDTO(Student student) {
        this.id = student.getId();
        this.nume = student.getNume();
        this.prenume = student.getPrenume();
        this.email = student.getEmail();
        this.cicluStudii = student.getCicluStudii().name();
        this.anStudiu = student.getAnStudiu();
        this.grupa = student.getGrupa();
    }
}
